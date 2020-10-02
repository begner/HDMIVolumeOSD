package com.begner.hdmivolumeosd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import java.lang.Math.floor

class VolumeLevelOSDView(applicationContext: Context, view: FrameLayout) {

    lateinit var context: Context
    lateinit var osdView: FrameLayout
    lateinit var settingsOSD  : SettingsOSD
    lateinit var osdPosition : OSDPosition

    init {
        context = applicationContext
        osdView = view
        settingsOSD = SettingsOSD(context)
        osdPosition = OSDPositionsVolume().getPositionByKey(settingsOSD.getPosition())
    }

    open fun addView(curVolume: Int, maxVolume: Int):VolumeLevelOSDView {

        val view : View
        if (osdPosition.isHorizontal) {
            view = LayoutInflater.from(context).inflate(R.layout.volume_osd_horizontal, null);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.volume_osd_vertical, null);
        }

        view.setPadding(
            settingsOSD.getPadding(),
            settingsOSD.getPadding(),
            settingsOSD.getPadding(),
            settingsOSD.getPadding()
        )

        val title = view.findViewById<TextView>(R.id.vosd_volume)
        title.text = mapVolume(curVolume).toString() // + "/" + mapVolume(props.maxVolume).toString()

        val bar = view.findViewById<ProgressBar>(R.id.vosd_bar)
        bar.max = mapVolume(maxVolume)
        bar.progress = mapVolume(curVolume)

        val speakerIcon = view.findViewById<ImageView>(R.id.vosd_icon_speaker)
        if (mapVolume(curVolume) < 1) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_0)
        } else if (mapVolume(curVolume) < 8) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_1)
        } else if (mapVolume(curVolume) < 15) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_2)
        } else {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_3)
        }


        // inflate the popup layout
        val metrics = context.getResources().getDisplayMetrics()

        var newWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        var newHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        if (osdPosition.isHorizontal) {
            newWidth = Math.round(
                metrics.widthPixels.toFloat() / 100f * settingsOSD.getSize().toFloat()
            )
        } else {
            newHeight = Math.round(
                metrics.heightPixels.toFloat() / 100f * settingsOSD.getSize().toFloat()
            )
        }

        osdView.addView(view, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = osdPosition.gravity
            width = newWidth
            height = newHeight
        })
        return this
    }



    fun addBackground():VolumeLevelOSDView {
        var backgroundWidth = 0
        var backgroundHeight = 0
        val backgroundView = ImageView(context).apply {
            if (osdPosition.isHorizontal) {
                setImageResource(R.drawable.layout_dimmer_horizontal)
                backgroundWidth = ViewGroup.LayoutParams.MATCH_PARENT
                backgroundHeight = 120 + settingsOSD.getPadding()
            }
            else {
                setImageResource(R.drawable.layout_dimmer_vertical)
                backgroundWidth = 120 + settingsOSD.getPadding()
                backgroundHeight = ViewGroup.LayoutParams.MATCH_PARENT
            }

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

    fun mapVolume(volume: Int): Int {
        var versatz = floor(volume.toDouble() / 6.toDouble()).toInt()
        return volume - versatz
    }
}