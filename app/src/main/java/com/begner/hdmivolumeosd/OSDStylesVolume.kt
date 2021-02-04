package com.begner.hdmivolumeosd


class OSDStylesVolume() : OSDStyles() {
    init {
        availableStyles = listOf(
            OSDStyle("android","Android").apply {
                layoutIDVertical = R.layout.volume_osd_vertical
                layoutIDHorizontal = R.layout.volume_osd_horizontal
                backgroundIDVertical = R.drawable.layout_dimmer_vertical
                backgroundIDHorizontal = R.drawable.layout_dimmer_horizontal
                animationDuration = 0
            },
            OSDStyle("apple14","Apple TvOS").apply {
                layoutIDVertical = R.layout.volume_osd_vertical_apple
                layoutIDHorizontal = R.layout.volume_osd_horizontal_apple
                backgroundIDVertical = null
                backgroundIDHorizontal = null
                animationDuration = 500
            }
        )
    }
}