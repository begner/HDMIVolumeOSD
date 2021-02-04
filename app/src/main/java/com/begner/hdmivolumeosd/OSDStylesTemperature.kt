package com.begner.hdmivolumeosd


class OSDStylesTemperature() : OSDStyles() {
    init {
        availableStyles = listOf(
            OSDStyle("android","Android").apply {
                layoutID = R.layout.temperature_osd
                backgroundID = R.drawable.layout_dimmer_circular
                animationDuration = 0
            },
            OSDStyle("apple14","Apple TvOS").apply {
                layoutID = R.layout.temperature_osd
                backgroundID = null
                animationDuration = 500
            }
        )
    }
}