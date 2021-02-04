package com.begner.hdmivolumeosd

import android.animation.ObjectAnimator
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

class OSD() {
    private lateinit var settingsContentObserver: ContentObserver
    private lateinit var applicationContext: Context
    private var isVisible: Boolean = false
    private lateinit var service: MainService
    private val mHandler: Handler? = Looper.myLooper()?.let { Handler(it) }
    private var mOverlay: FrameLayout? = null
    private var viewVolume : OSDViewVolume? = null
    private var viewTemperature: OSDViewTemperature? = null
    private var initialized: Boolean = false

    fun initialize(s: MainService, c: Context) {
        if (!initialized) {
            initialized = true
            service = s
            applicationContext = c
            settingsContentObserver = SettingsContentObserver(service, this, Looper.myLooper()?.let { Handler(it) })
            applicationContext.contentResolver.registerContentObserver(
                Settings.System.CONTENT_URI,
                true,
                settingsContentObserver
            )
        }
    }

    fun destroy() {
        removePopup(true)
        applicationContext.contentResolver.unregisterContentObserver(settingsContentObserver)
    }

    fun createOverlay() {
        if (!Settings.canDrawOverlays(applicationContext)) {
            return
        }


        mOverlay = when (val overlay = mOverlay) {
            is FrameLayout -> overlay
            else -> FrameLayout(service).apply {
                val params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT
                )
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                wm.addView(this, params)
            }
        }.also {
            viewVolume = OSDViewVolume(applicationContext, it)
            viewTemperature = OSDViewTemperature(applicationContext, it)
        }
        return
    }

    private fun removePopup(removeOverlay: Boolean = false) {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null)
        }
        if (mOverlay != null) {
            mOverlay!!.apply {
                removeAllViews()
                if (removeOverlay) {
                    val wm =
                        applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    wm.removeViewImmediate(mOverlay)
                    mOverlay = null
                }
            }
        }
    }

    fun updateViewVolume(curVolume: Int, oldVolume: Int, maxVolume: Int) {
        viewVolume!!.update(curVolume, oldVolume, maxVolume)
        viewTemperature!!.update(service.getAverageTemp(), service.getLastMqttValueString())
    }

    class SettingsContentObserver internal constructor(c: Context, s: OSD, handler: Handler?) :
        ContentObserver(handler) {
        var context: Context
        var service: OSD
        var oldVolume: Int = -1
        var currentVolume: Int = 0

        override fun deliverSelfNotifications(): Boolean {
            return super.deliverSelfNotifications()
        }

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            oldVolume = currentVolume
            currentVolume = Objects.requireNonNull(audio).getStreamVolume(AudioManager.STREAM_MUSIC)

            val mediaRouter = context.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter
            val routeInfo = mediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_AUDIO);
            val routeName = routeInfo.getName().toString()
            var routeMax = routeInfo.getVolumeMax()

            if (routeName.equals("HDMI") || !SettingsGlobal(context).getLimitOnHDMI()) {
                service.updateViewVolume(currentVolume, oldVolume, routeMax)
            }
        }

        init {
            context = c
            service = s
        }
    }
}
