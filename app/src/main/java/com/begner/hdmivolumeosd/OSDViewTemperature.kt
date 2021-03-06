package com.begner.hdmivolumeosd

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class OSDViewTemperature(applicationContext: Context, frameLayout: FrameLayout) : OSDView(
    applicationContext,
    frameLayout
) {

    private var settingsTemperature  : SettingsTemperature = SettingsTemperature(context)
    private lateinit var temperatureDisplay : TextView
    private lateinit var lastMqttDisplay : TextView
    private lateinit var animationContainerScale : View
    private lateinit var animationContainerBar : View
    private lateinit var bar : LayoutTemperatureIndicator
    val mainHandler = Handler(Looper.getMainLooper())
    private var lastUpdated : Long = 0

    init {
        osdPosition = OSDPositionsTemperature().getPositionByKey(settingsTemperature.getPosition())
        osdStyle = OSDStylesTemperature().getPositionByKey(settingsTemperature.getStyle())
        start()
    }

    override fun getAnimationSubPart(name: String): View? {
        var view: View? = null
        when (name) {
            "scale" -> view = animationContainerScale
            "bar" -> view = animationContainerBar
        }
        return view
    }

    override fun addView() {
        view = LayoutInflater.from(context).inflate(osdStyle.getLayout(false), null)
        view.visibility = View.GONE
        if (!settingsTemperature.getMQTTActive()) {
            return
        }

        view.setPadding(
            settingsTemperature.getPadding(),
            settingsTemperature.getPadding(),
            settingsTemperature.getPadding(),
            settingsTemperature.getPadding()
        )

        temperatureDisplay = view.findViewById(R.id.vosd_temp)

        lastMqttDisplay= view.findViewById(R.id.vosd_mqtt_time)

        val osdContainer = view.findViewById<ConstraintLayout>(R.id.vosd_osd_container)

        animationContainerScale = view.findViewById(R.id.vosd_scale_animation_container)
        animationContainerBar = view.findViewById(R.id.vosd_bar_animation_container)

        bar = view.findViewById(R.id.vosd_bar)

        val barContainer = view.findViewById<FrameLayout>(R.id.vosd_bar_container)
        barContainer.rotation = osdPosition.layoutRotation
        barContainer.rotationX = osdPosition.layoutRotationX
        barContainer.rotationY = osdPosition.layoutRotationY

        val constraintSet = ConstraintSet()
        constraintSet.clone(osdContainer)

        val displayContainer = view.findViewById<ConstraintLayout>(R.id.vosd_bar_display)
        constraintSet.clear(displayContainer.id, ConstraintSet.LEFT)
        constraintSet.clear(displayContainer.id, ConstraintSet.RIGHT)
        constraintSet.clear(displayContainer.id, ConstraintSet.TOP)
        constraintSet.clear(displayContainer.id, ConstraintSet.BOTTOM)
        constraintSet.connect(
            displayContainer.id,
            osdPosition.displayConstraintY,
            ConstraintSet.PARENT_ID,
            osdPosition.displayConstraintY,
            0
        )
        constraintSet.connect(
            displayContainer.id,
            osdPosition.displayConstraintX,
            ConstraintSet.PARENT_ID,
            osdPosition.displayConstraintX,
            0
        )

        val icon = view.findViewById<ImageView>(R.id.vosd_icon_temp)
        if (icon != null) {
            constraintSet.clear(icon.id, ConstraintSet.LEFT)
            constraintSet.clear(icon.id, ConstraintSet.RIGHT)
            constraintSet.clear(icon.id, ConstraintSet.TOP)
            constraintSet.clear(icon.id, ConstraintSet.BOTTOM)
            constraintSet.connect(
                icon.id,
                osdPosition.displayConstraintY,
                ConstraintSet.PARENT_ID,
                osdPosition.displayConstraintY,
                0
            )
            constraintSet.connect(
                icon.id,
                osdPosition.displayConstraintX,
                ConstraintSet.PARENT_ID,
                osdPosition.displayConstraintX,
                0
            )
        }

        constraintSet.applyTo(osdContainer)

        frameLayout.addView(view, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = osdPosition.gravity
        })

        startClockUpdateTimer()
    }

    private fun formatTime(value: Long): String {
        val timeDiff = System.currentTimeMillis() - value
        val date = Date(timeDiff)
        val formatter = SimpleDateFormat("m:ss", Locale.US)
        return formatter.format(date)
    }

    private fun startClockUpdateTimer() {
        mainHandler.post(object : Runnable {
            override fun run() {
                updateClock()
                mainHandler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateClock() {
        if (isVisible) {
            lastMqttDisplay.text = formatTime(lastUpdated)
        }
    }

    fun update(currentTemperature: Float, lastMqttUpdate: Long) {
        if (!settingsTemperature.getMQTTActive()) {
            return
        }

        val tempRounded = (currentTemperature * 10).roundToInt().toFloat() / 10f
        temperatureDisplay.text = tempRounded.toString()

        lastUpdated = lastMqttUpdate
        updateClock()

        bar.maxValue = settingsTemperature.getMaxTemp()
        bar.minValue = settingsTemperature.getMinTemp()
        val curVal = currentTemperature.toInt()

        bar.value = curVal

        updated()
    }

    override fun addBackground() {
        if (!settingsTemperature.getMQTTActive()) {
            return
        }
        val backgroundRes = osdStyle.getBackground(osdPosition.isHorizontal) ?: return

        backgroundView = ImageView(context).apply {
            setImageResource(backgroundRes)
            // set the ImageView bounds to match the Drawable's dimensions
            adjustViewBounds = true
        }
        backgroundView!!.visibility = View.GONE

        backgroundView!!.rotation = osdPosition.backgroundRotation

        frameLayout.addView(
            backgroundView, FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                osdPosition.gravity
            )
        )
    }
}