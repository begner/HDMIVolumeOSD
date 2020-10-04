package com.begner.hdmivolumeosd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.lang.Math.round

class OSDViewTemperature(applicationContext: Context, view: FrameLayout) {

    var context: Context
    var osdView: FrameLayout
    var settings  : SettingsTemperature
    var osdPosition : OSDPosition

    init {
        context = applicationContext
        osdView = view
        settings = SettingsTemperature(context)
        osdPosition = OSDPositionsTemperature().getPositionByKey(settings.getPosition())
    }

    fun addView(currentTemperature: Float):OSDViewTemperature {

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
        bar.max = settingsMQTT.getMaxTemp() - settingsMQTT.getMinTemp()
        var progress = currentTemperature.toInt() - settingsMQTT.getMinTemp()
        if (progress < 0) {
            progress = 0
        }
        bar.progress = progress

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
        val backgroundView = ImageView(context).apply {
            setImageResource(osdPosition.backgroundID)
            // set the ImageView bounds to match the Drawable's dimensions
            adjustViewBounds = true
        }
        backgroundView.rotation = osdPosition.backgroundRotation

        osdView.addView(
            backgroundView, FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                osdPosition.gravity
            )
        )
        return this
    }
}