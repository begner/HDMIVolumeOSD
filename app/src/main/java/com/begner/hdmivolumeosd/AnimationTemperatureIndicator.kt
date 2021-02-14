package com.begner.hdmivolumeosd

import android.view.animation.Animation
import android.view.animation.Transformation

class AnimationTemperatureIndicator (
    private val tempIndicator: LayoutTemperatureIndicator,
    private val from: Float,
    private val to: Float
) :
    Animation() {
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        tempIndicator.value = value.toInt()
    }
}
