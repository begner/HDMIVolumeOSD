package com.begner.hdmivolumeosd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_settings_mqtt.view.*
import java.lang.Math.round

class TemperatureOSDView(applicationContext: Context, view: FrameLayout) {

    lateinit var context: Context
    lateinit var osdView: FrameLayout
    lateinit var settings  : SettingsMQTT
    lateinit var osdPosition : OSDPosition

    init {
        context = applicationContext
        osdView = view
        settings = SettingsMQTT(context)
        osdPosition = OSDPositionsTemperature().getPositionByKey(settings.getPosition())
    }

    open fun addView(currentTemperature: Float, maxTemperature: Int):TemperatureOSDView {

        val view : View = LayoutInflater.from(context).inflate(R.layout.temperature_osd, null);

        val settingsMQTT = SettingsMQTT(context)

        val temp = view.findViewById<TextView>(R.id.vosd_temp)
        if (settingsMQTT.getMQTTActive()) {
            val tempRounded = round(currentTemperature * 10).toFloat() / 10f
            temp.text = tempRounded.toString()
        } else {
            temp.visibility = View.GONE
        }

        val bar = view.findViewById<ProgressBar>(R.id.vosd_bar)
        bar.max = maxTemperature
        bar.progress = currentTemperature.toInt()

        val barContainer = view.findViewById<FrameLayout>(R.id.vosd_bar_container)
        barContainer.rotation = osdPosition.dimmerRotation

        osdView.addView(view, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = osdPosition.gravity
        })


        return this
    }

    fun addBackground():TemperatureOSDView {
        var backgroundWidth = 0
        var backgroundHeight = 0
        val backgroundView = ImageView(context).apply {
            setImageResource(R.drawable.layout_dimmer_circular)
            backgroundWidth = 250
            backgroundHeight = 250

            // set the ImageView bounds to match the Drawable's dimensions
            adjustViewBounds = true
        }
        backgroundView.rotation = osdPosition.dimmerRotation

        osdView.addView(
            backgroundView, FrameLayout.LayoutParams(
                backgroundWidth,
                backgroundHeight,
                osdPosition.gravity
            )
        )
        return this
    }
}