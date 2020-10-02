package com.begner.hdmivolumeosd

import android.view.Gravity

class OSDPositionsTemperature() : OSDPositions() {
    init {
        availablePositions = listOf(
            OSDPosition("topLeft", "Top Left", Gravity.TOP or Gravity.START).apply {
                layoutID = R.layout.temperature_osd
                layoutRotation = 90f
                layoutRotationX = 180f
                layoutRotationY = 0f
                backgroundID = R.drawable.layout_dimmer_circular
                backgroundRotation = 0f
            },
            OSDPosition("topRight","Top Right", Gravity.TOP or Gravity.END).apply {
                layoutID = R.layout.temperature_osd
                layoutRotation = 0f
                layoutRotationX = 180f
                layoutRotationY = 0f
                backgroundID = R.drawable.layout_dimmer_circular
                backgroundRotation = 270f
            },
            OSDPosition("bottomLeft", "Bottom Left", Gravity.BOTTOM or Gravity.START).apply {
                layoutID = R.layout.temperature_osd
                layoutRotation = 0f
                layoutRotationX = 0f
                layoutRotationY = 180f
                backgroundID = R.drawable.layout_dimmer_circular
                backgroundRotation = 90f
            },
            OSDPosition("bottomRight", "Bottom Right", Gravity.BOTTOM or Gravity.END).apply {
                layoutID = R.layout.temperature_osd
                layoutRotation = 0f
                layoutRotationX = 0f
                layoutRotationY = 0f
                backgroundID = R.drawable.layout_dimmer_circular
                backgroundRotation = 180f
            },
        )
    }
}