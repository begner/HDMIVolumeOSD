package com.begner.hdmivolumeosd

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import java.lang.Math.floor


class OSDViewVolume(applicationContext: Context, frameLayout: FrameLayout) : OSDView(applicationContext, frameLayout) {

    var settingsVolume  : SettingsVolume
    var title : TextView? = null
    lateinit var bar : ProgressBar
    lateinit var speakerIcon : ImageView
    var progressAnimation : Animation? = null

    init {
        settingsVolume = SettingsVolume(context)
        osdPosition = OSDPositionsVolume().getPositionByKey(settingsVolume.getPosition())
        osdStyle = OSDStylesVolume().getPositionByKey(settingsVolume.getStyle())
        addBackground()
        addView()
    }

    public fun update(curVolume: Int, oldVolume: Int, maxVolume: Int) {
        if (title != null) {
            title!!.text = mapVolume(curVolume).toString() // + "/" + mapVolume(props.maxVolume).toString()
        }

        if (mapVolume(curVolume) < 1) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_0)
        } else if (mapVolume(curVolume) < 8) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_1)
        } else if (mapVolume(curVolume) < 15) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_2)
        } else {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_3)
        }

        val animationFactor = 1000
        var oldVolumeValue = oldVolume
        if (oldVolume == -1) {
            oldVolumeValue = curVolume
        }
        val oldProgress = mapVolume(oldVolumeValue).toFloat() * animationFactor
        val newProgress = mapVolume(curVolume).toFloat() * animationFactor
        bar.max = (mapVolume(maxVolume).toFloat() * animationFactor).toInt()
        if (progressAnimation != null) {
            progressAnimation!!.cancel()
        }
        progressAnimation = AnimationProgressBarProgress(bar, oldProgress, newProgress)
        progressAnimation!!.setDuration(osdStyle.animationDuration);
        bar.startAnimation(progressAnimation);

        updated()
    }

    override public fun addView() {
        val layoutId = osdStyle.getLayout(osdPosition.isHorizontal)
        view = LayoutInflater.from(context).inflate(layoutId, null);
        view.visibility = View.GONE

        val curVolume = 0
        val oldVolume = 0
        val maxVolume = 60

        view.setPadding(
            settingsVolume.getPadding(),
            settingsVolume.getPadding(),
            settingsVolume.getPadding(),
            settingsVolume.getPadding()
        )

        title = view.findViewById<TextView>(R.id.vosd_volume)

        bar = view.findViewById<ProgressBar>(R.id.vosd_bar)

        speakerIcon = view.findViewById<ImageView>(R.id.vosd_icon_speaker)

        // inflate the popup layout
        val metrics = context.getResources().getDisplayMetrics()
        var newWidth = ViewGroup.LayoutParams.WRAP_CONTENT
        var newHeight = ViewGroup.LayoutParams.WRAP_CONTENT
        if (osdPosition.isHorizontal) {
            newWidth = Math.round(
                metrics.widthPixels.toFloat() / 100f * settingsVolume.getSize().toFloat()
            )
        } else {
            newHeight = Math.round(
                metrics.heightPixels.toFloat() / 100f * settingsVolume.getSize().toFloat()
            )
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

    override public fun addBackground() {
        val backgroundRes = osdStyle.getBackground(osdPosition.isHorizontal)
        if (backgroundRes == null) {
            return
        }

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

    private fun mapVolume(volume: Int): Int {
        var versatz = floor(volume.toDouble() / 6.toDouble()).toInt()
        return volume - versatz
    }
}