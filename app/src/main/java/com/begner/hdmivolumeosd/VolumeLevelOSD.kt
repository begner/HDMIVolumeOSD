package nl.rogro82.pipup

import java.math.RoundingMode
import android.app.Service
import android.content.Context
import android.database.ContentObserver
import android.graphics.PixelFormat
import android.media.AudioManager
import android.media.MediaRouter
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.begner.hdmivolumeosd.MainService
import java.lang.Math.round
import java.util.*

class VolumeLevelOSD(s: MainService, c: Context) {
    private var settingsContentObserver: ContentObserver
    private var applicationContext: Context = c
    private var service: MainService = s

    private val mHandler: Handler = Handler()
    private var mOverlay: FrameLayout? = null
    private var mPopup: VolumeLevelOSDView? = null

    init {
        settingsContentObserver = SettingsContentObserver(service, this, Handler())
        applicationContext.contentResolver.registerContentObserver(
            Settings.System.CONTENT_URI,
            true,
            settingsContentObserver
        )
    }

    fun destroy() {
        applicationContext.contentResolver.unregisterContentObserver(settingsContentObserver)
    }

    fun createOverlay(maxVolume : Int, currentVolume : Int, currentTemp : Float) {

        removePopup()

        val props = VolumeLevelOSDProps(
            currentVolume,
            maxVolume,
            currentTemp
        )

        mOverlay = when (val overlay = mOverlay) {
            is FrameLayout -> overlay
            else -> FrameLayout(service).apply {

                setPadding(20, 20, 20, 20)

                val params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
                )

                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.addView(this, params)
            }
        }.also {

            // inflate the popup layout
            mPopup = VolumeLevelOSDView.build(applicationContext, props)

            it.addView(mPopup, FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ). apply {

                // position the popup

                gravity = when(props.position) {
                    VolumeLevelOSDProps.Position.TopRight -> Gravity.TOP or Gravity.END
                    VolumeLevelOSDProps.Position.TopLeft -> Gravity.TOP or Gravity.START
                    VolumeLevelOSDProps.Position.BottomRight -> Gravity.BOTTOM or Gravity.END
                    VolumeLevelOSDProps.Position.BottomLeft -> Gravity.BOTTOM or Gravity.START
                    VolumeLevelOSDProps.Position.Center -> Gravity.CENTER
                }
            })
        }

        mHandler.postDelayed({
            removePopup(true)
        }, (props.duration * 1000).toLong())
    }


    private fun removePopup(removeOverlay: Boolean = false) {
        mHandler.removeCallbacksAndMessages(null)
        mPopup = mPopup?.let {
            it.destroy()
            null
        }
        mOverlay?.apply {
            removeAllViews()
            if (removeOverlay) {
                val wm = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.removeViewImmediate(mOverlay)
                mOverlay = null
            }
        }
    }


    class SettingsContentObserver internal constructor(c: Context, s: VolumeLevelOSD, handler: Handler?) :
        ContentObserver(handler) {
        var context: Context
        var service: VolumeLevelOSD

        override fun deliverSelfNotifications(): Boolean {
            return super.deliverSelfNotifications()
        }

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume: Int = Objects.requireNonNull(audio).getStreamVolume(AudioManager.STREAM_MUSIC)

            val mediaRouter = context.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter
            val routeInfo = mediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_AUDIO);
            val routeName = routeInfo.getName().toString()
            var routeMax = routeInfo.getVolumeMax()

           // if (routeName.equals("HDMI")) {
                // val message: String = "DirectVolume: " + currentVolume.toString() + "/" + routeMax.toString()
                // val props = PopupProps(4,PopupProps.Position.BottomRight,"#88000000", message, 16f)
                service.createOverlay(routeMax, currentVolume, service.service.getAverageTemp())
                //service.createPopup(props)
            // }

        }

        init {
            context = c
            service = s
        }
    }

}
