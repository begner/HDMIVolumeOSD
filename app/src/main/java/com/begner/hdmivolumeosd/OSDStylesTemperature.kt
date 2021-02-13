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
                animationDelay = 0
                animationDuration = 500
            },
            OSDStyle("apple14radial","Apple TvOS, Radial Animation").apply {
                layoutID = R.layout.temperature_osd_apple
                animationClass = StyleAnimationTemperatureAppleRadial()
                backgroundID = null
                animationDelay = 300
                animationDuration = 500
            }
        )
    }
}