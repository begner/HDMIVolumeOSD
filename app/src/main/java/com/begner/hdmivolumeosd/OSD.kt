package com.begner.hdmivolumeosd

import android.content.Context
import android.database.ContentObserver
import android.graphics.PixelFormat
import android.media.AudioManager
import android.media.MediaRouter
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.WindowManager
import android.widget.FrameLayout
import java.util.*

class OSD(s: MainService, c: Context) {
    private var settingsContentObserver: ContentObserver
    private var applicationContext: Context = c
    private var service: MainService = s
    private val mHandler: Handler? = Looper.myLooper()?.let { Handler(it) }
    private var mOverlay: FrameLayout? = null

    init {
        settingsContentObserver = SettingsContentObserver(service, this, Looper.myLooper()?.let { Handler(it) })
        applicationContext.contentResolver.registerContentObserver(
            Settings.System.CONTENT_URI,
            true,
            settingsContentObserver
        )
    }

    fun destroy() {
        applicationContext.contentResolver.unregisterContentObserver(settingsContentObserver)
    }

    fun createOverlay(maxVolume : Int, currentVolume : Int) {
        if (!Settings.canDrawOverlays(applicationContext))
        {
            return
        }

        removePopup()

        mOverlay = when (val overlay = mOverlay) {
            is FrameLayout -> overlay
            else -> FrameLayout(service).apply {
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
            OSDViewVolume(applicationContext, it)
                .addBackground()
                .addView(currentVolume, maxVolume)

            OSDViewTemperature(applicationContext, it)
                .addBackground()
                .addView(service.getAverageTemp())
        }

        val settingsGlobal = SettingsGlobal(applicationContext)
        if (mHandler != null) {
            mHandler.postDelayed({
                removePopup(true)
            }, (settingsGlobal.getDuration() * 1000).toLong())
        }
    }

    private fun removePopup(removeOverlay: Boolean = false) {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null)
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

    class SettingsContentObserver internal constructor(c: Context, s: OSD, handler: Handler?) :
        ContentObserver(handler) {
        var context: Context
        var service: OSD

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

            if (routeName.equals("HDMI") || !SettingsGlobal(context).getLimitOnHDMI()) {
                service.createOverlay(routeMax, currentVolume)
            }
        }

        init {
            context = c
            service = s
        }
    }
}
