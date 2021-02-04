package com.begner.hdmivolumeosd

import android.view.Gravity
import android.widget.ScrollView

class OSDPositionsTemperature() : OSDPositions() {
    init {
        availablePositions = listOf(
            OSDPosition("topLeft", "Top Left", Gravity.TOP or Gravity.START).apply {
                layoutRotation = 90f
                layoutRotationX = 180f
                layoutRotationY = 0f
                backgroundRotation = 180f
            },
            OSDPosition("topRight","Top Right", Gravity.TOP or Gravity.END).apply {
                layoutRotation = 0f
                layoutRotationX = 180f
                layoutRotationY = 0f
                backgroundRotation = 270f
            },
            OSDPosition("bottomLeft", "Bottom Left", Gravity.BOTTOM or Gravity.START).apply {
                layoutRotation = 0f
                layoutRotationX = 0f
                layoutRotationY = 180f
                backgroundRotation = 90f
            },
            OSDPosition("bottomRight", "Bottom Right", Gravity.BOTTOM or Gravity.END).apply {
                layoutRotation = 0f
                layoutRotationX = 0f
                layoutRotationY = 0f
                backgroundRotation = 0f
            },
        )
    }
}

