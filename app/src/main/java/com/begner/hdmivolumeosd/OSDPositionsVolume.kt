package com.begner.hdmivolumeosd

import android.view.Gravity

class OSDPositionsVolume() : OSDPositions() {
    init {
        availablePositions = listOf(

            OSDPosition("leftTop","Left Top", Gravity.TOP or Gravity.START, 0f,false),
            OSDPosition("leftCenter","Left", Gravity.CENTER or Gravity.START, 0f,false),
            OSDPosition("leftBottom","Left Bottom", Gravity.BOTTOM or Gravity.START, 0f,false),

            OSDPosition("topLeft", "Top Left", Gravity.TOP or Gravity.START),
            OSDPosition("topCenter", "Top", Gravity.TOP or Gravity.CENTER),
            OSDPosition("topRight","Top Right", Gravity.TOP or Gravity.END ),

            OSDPosition("rightTop","Right Top", Gravity.TOP or Gravity.END, 180f,false),
            OSDPosition("rightCenter","Right", Gravity.CENTER or Gravity.END, 180f, false),
            OSDPosition("rightBottom","Right Bottom", Gravity.BOTTOM or Gravity.END,180f, false),

            OSDPosition("bottomLeft", "Bottom Left", Gravity.BOTTOM or Gravity.START, 180f),
            OSDPosition("bottomCenter", "Bottom", Gravity.BOTTOM or Gravity.CENTER, 180f),
            OSDPosition("bottomRight", "Bottom Right", Gravity.BOTTOM or Gravity.END, 180f),

        )
    }
}