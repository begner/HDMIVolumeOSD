package com.begner.hdmivolumeosd

import android.view.Gravity

class OSDPositionsTemperature() : OSDPositions() {
    init {
        availablePositions = listOf(
            OSDPosition("topLeft", "Top Left", Gravity.TOP or Gravity.START, 180f),
            OSDPosition("topRight","Top Right", Gravity.TOP or Gravity.END , 270f),
            OSDPosition("bottomLeft", "Bottom Left", Gravity.BOTTOM or Gravity.START, 90f),
            OSDPosition("bottomRight", "Bottom Right", Gravity.BOTTOM or Gravity.END, 0f),

        )
    }
}