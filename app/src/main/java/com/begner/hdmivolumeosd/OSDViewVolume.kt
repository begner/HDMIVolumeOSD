package com.begner.hdmivolumeosd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.math.roundToInt


class OSDViewVolume(applicationContext: Context, frameLayout: FrameLayout) : OSDView(applicationContext, frameLayout) {

    private var settingsVolume  : SettingsVolume = SettingsVolume(context)
    private var title : TextView? = null
    private lateinit var bar : ProgressBar
    private lateinit var speakerIcon : ImageView
    private var progressAnimation : Animation? = null
    private var osdMapping: OSDMapping = OSDMappingVolume().getPositionByKey(settingsVolume.getMapping())
    private val animationResolutionFactor = 100

    init {
        osdPosition = OSDPositionsVolume().getPositionByKey(settingsVolume.getPosition())
        osdStyle = OSDStylesVolume().getPositionByKey(settingsVolume.getStyle())
        start()
    }

    fun update(curVolume: Int, oldVolume: Int, maxVolume: Int) {
        if (title != null) {
            title!!.text = mapVolume(curVolume, maxVolume).toString() // + "/" + mapVolume(props.maxVolume).toString()
        }


        var oldVolumeValue = oldVolume
        if (oldVolume == -1) {
            oldVolumeValue = curVolume
        }

        val mappedOldVolume = mapVolume(oldVolumeValue, maxVolume)
        val mappedCurVolume = mapVolume(curVolume, maxVolume)
        val mappedMaxVolume = mapVolume(maxVolume, maxVolume)

        when (mappedCurVolume) {
            0 -> speakerIcon.setImageResource(R.drawable.ic_icon_speaker_0)
            in 1..8 -> speakerIcon.setImageResource(R.drawable.ic_icon_speaker_1)
            in 9..15 -> speakerIcon.setImageResource(R.drawable.ic_icon_speaker_2)
            else -> speakerIcon.setImageResource(R.drawable.ic_icon_speaker_3)
        }

        val oldProgress = mappedOldVolume.toFloat() * animationResolutionFactor
        val newProgress = mappedCurVolume.toFloat() * animationResolutionFactor
        bar.max = (mappedMaxVolume.toFloat() * animationResolutionFactor).toInt()
        if (progressAnimation != null) {
            progressAnimation!!.cancel()
        }
        progressAnimation = AnimationProgressBarProgress(bar, oldProgress, newProgress)
        progressAnimation!!.duration = osdStyle.animationDuration
        bar.startAnimation(progressAnimation)

        updated()
    }

    override fun addView() {
        val layoutId = osdStyle.getLayout(osdPosition.isHorizontal)
        view = LayoutInflater.from(context).inflate(layoutId, null)
        view.visibility = View.GONE

        view.setPadding(
            settingsVolume.getPadding(),
            settingsVolume.getPadding(),
            settingsVolume.getPadding(),
            settingsVolume.getPadding()
        )

        title = view.findViewById(R.id.vosd_volume)

        bar = view.findViewById(R.id.vosd_bar)

        speakerIcon = view.findViewById(R.id.vosd_icon_speaker)

        // inflate the popup layout
        val metrics = context.resources.displayMetrics
        var newWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        var newHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        if (osdPosition.isHorizontal) {
            newWidth = (metrics.widthPixels.toFloat() / 100f * settingsVolume.getSize()
                .toFloat()).roundToInt()
        } else {
            newHeight = (metrics.heightPixels.toFloat() / 100f * settingsVolume.getSize()
                .toFloat()).roundToInt()
        }

        frameLayout.addView(view, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            gravity = osdPosition.gravity
            width = newWidth
            height = newHeight
        })
    }

    override fun addBackground() {
        val backgroundRes = osdStyle.getBackground(osdPosition.isHorizontal) ?: return

        var backgroundWidth = ViewGroup.LayoutParams.MATCH_PARENT
        var backgroundHeight = ViewGroup.LayoutParams.MATCH_PARENT
        backgroundView = ImageView(context).apply {
            setImageResource(backgroundRes)
            if (osdPosition.isHorizontal) {
                backgroundHeight = 120 + settingsVolume.getPadding()
            }
            else {
                backgroundWidth = 120 + settingsVolume.getPadding()
            }

            // set the ImageView bounds to match the Drawable's dimensions
            adjustViewBounds = true
        }
        backgroundView!!.visibility = View.GONE
        backgroundView!!.rotation = osdPosition.backgroundRotation

        frameLayout.addView(backgroundView, FrameLayout.LayoutParams(
                backgroundWidth,
                backgroundHeight
        ).apply {
            gravity = osdPosition.gravity
        })
    }

    private fun mapVolume(volume: Int, maxVolume: Int): Int {
        return osdMapping.mappingClass.mapVolume(volume, maxVolume)
    }
}