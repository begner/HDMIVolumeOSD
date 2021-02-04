package com.begner.hdmivolumeosd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.lang.Math.round

class OSDViewTemperature(applicationContext: Context, frameLayout: FrameLayout) : OSDView(applicationContext, frameLayout) {

    var settingsTemperature  : SettingsTemperature
    lateinit var temp : TextView
    lateinit var lastMqtt : TextView
    lateinit var bar : LayoutTemperatureIndicator

    init {
        settingsTemperature = SettingsTemperature(context)
        settingsGlobal = SettingsGlobal(context)
        osdPosition = OSDPositionsTemperature().getPositionByKey(settingsTemperature.getPosition())
        osdStyle = OSDStylesTemperature().getPositionByKey(settingsTemperature.getStyle())
        addBackground()
        addView()
    }

    override public fun addView() {
        val currentTemperature = 0f
        val lastMqttView = ""

        view = LayoutInflater.from(context).inflate(osdStyle.getLayout(false), null);
        view.visibility = View.GONE
        if (!settingsTemperature.getMQTTActive()) {
            return
        }

        temp = view.findViewById<TextView>(R.id.vosd_temp)

        lastMqtt = view.findViewById<TextView>(R.id.vosd_mqtt_time)

        bar = view.findViewById<LayoutTemperatureIndicator>(R.id.vosd_bar)

        val barContainer = view.findViewById<FrameLayout>(R.id.vosd_bar_container)
        barContainer.rotation = osdPosition.layoutRotation
        barContainer.rotationX = osdPosition.layoutRotationX
        barContainer.rotationY = osdPosition.layoutRotationY

        frameLayout.addView(view, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = osdPosition.gravity
        })
    }

    public fun update(currentTemperature: Float, lastMqttView: String) {
        val tempRounded = round(currentTemperature * 10).toFloat() / 10f
        temp.text = tempRounded.toString()

        lastMqtt.text = lastMqttView

        bar.maxValue = settingsTemperature.getMaxTemp().toInt()
        bar.minValue = settingsTemperature.getMinTemp()
        bar.value = currentTemperature.toInt()

        updated()
    }

    override public fun addBackground() {
        if (!settingsTemperature.getMQTTActive()) {
            return
        }
        val backgroundRes = osdStyle.getBackground(osdPosition.isHorizontal)
        if (backgroundRes == null) {
            return
        }

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