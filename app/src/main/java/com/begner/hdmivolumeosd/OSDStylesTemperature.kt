package com.begner.hdmivolumeosd

import android.animation.Animator


class OSDStylesTemperature() : OSDStyles() {
    init {
        availableStyles = listOf(
            OSDStyle("android","Android").apply {
                layoutID = R.layout.temperature_osd
                backgroundID = R.drawable.layout_dimmer_circular
            },
            OSDStyle("apple14","Apple TvOS").apply {
                layoutID = R.layout.temperature_osd_apple
                animationClass = StyleAnimationTemperatureApple()
                backgroundID = null
            }
        )
    }
}