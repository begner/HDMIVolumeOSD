package com.begner.hdmivolumeosd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.lang.Math.round

class OSDViewTemperature(applicationContext: Context, view: FrameLayout) {

    lateinit var context: Context
    lateinit var osdView: FrameLayout
    lateinit var settings  : SettingsTemperature
    lateinit var osdPosition : OSDPosition

    init {
        context = applicationContext
        osdView = view
        settings = SettingsTemperature(context)
        osdPosition = OSDPositionsTemperature().getPositionByKey(settings.getPosition())
    }

    open fun addView(currentTemperature: Float):OSDViewTemperature {

        val view : View = LayoutInflater.from(context).inflate(osdPosition.layoutID, null);
        val settingsMQTT = SettingsTemperature(context)

        val temp = view.findViewById<TextView>(R.id.vosd_temp)
        if (settingsMQTT.getMQTTActive()) {
            val tempRounded = round(currentTemperature * 10).toFloat() / 10f
            temp.text = tempRounded.toString()
        } else {
            temp.visibility = View.GONE
        }

        val bar = view.findViewById<ProgressBar>(R.id.vosd_bar)
        bar.max = settingsMQTT.getMaxTemp()
        bar.progress = currentTemperature.toInt()

        val barContainer = view.findViewById<FrameLayout>(R.id.vosd_bar_container)
        barContainer.rotation = osdPosition.layoutRotation
        barContainer.rotationX = osdPosition.layoutRotationX
        barContainer.rotationY = osdPosition.layoutRotationY

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

    fun addBackground():OSDViewTemperature {
        var backgroundWidth = 0
        var backgroundHeight = 0
        val backgroundView = ImageView(context).apply {
            setImageResource(osdPosition.backgroundID)
            backgroundWidth = 250
            backgroundHeight = 250

            // set the ImageView bounds to match the Drawable's dimensions
            adjustViewBounds = true
        }
        backgroundView.rotation = osdPosition.backgroundRotation

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