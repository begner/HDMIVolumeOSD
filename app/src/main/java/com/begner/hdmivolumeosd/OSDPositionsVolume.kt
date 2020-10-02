package com.begner.hdmivolumeosd

import android.view.Gravity

class OSDPositionsVolume() : OSDPositions() {
    init {
        availablePositions = listOf(
            OSDPosition("leftTop","Left Top", Gravity.TOP or Gravity.START).apply {
                isHorizontal = false
                layoutID = R.layout.volume_osd_vertical
                backgroundID = R.drawable.layout_dimmer_vertical
                backgroundRotation = 0f
            },
            OSDPosition("leftCenter","Left", Gravity.CENTER or Gravity.START).apply {
                isHorizontal = false
                layoutID = R.layout.volume_osd_vertical
                backgroundID = R.drawable.layout_dimmer_vertical
                backgroundRotation = 0f
            },
            OSDPosition("leftBottom","Left Bottom", Gravity.BOTTOM or Gravity.START).apply {
                isHorizontal = false
                layoutID = R.layout.volume_osd_vertical
                backgroundID = R.drawable.layout_dimmer_vertical
                backgroundRotation = 0f
            },

            OSDPosition("topLeft", "Top Left", Gravity.TOP or Gravity.START).apply {
                isHorizontal = true
                layoutID = R.layout.volume_osd_horizontal
                backgroundID = R.drawable.layout_dimmer_horizontal
                backgroundRotation = 0f
            },
            OSDPosition("topCenter", "Top", Gravity.TOP or Gravity.CENTER).apply {
                isHorizontal = true
                layoutID = R.layout.volume_osd_horizontal
                backgroundID = R.drawable.layout_dimmer_horizontal
                backgroundRotation = 0f
            },
            OSDPosition("topRight","Top Right", Gravity.TOP or Gravity.END).apply {
                isHorizontal = true
                layoutID = R.layout.volume_osd_horizontal
                backgroundID = R.drawable.layout_dimmer_horizontal
                backgroundRotation = 0f
            },

            OSDPosition("rightTop","Right Top", Gravity.TOP or Gravity.END).apply {
                isHorizontal = false
                layoutID = R.layout.volume_osd_vertical
                backgroundID = R.drawable.layout_dimmer_vertical
                backgroundRotation = 180f
            },
            OSDPosition("rightCenter","Right", Gravity.CENTER or Gravity.END).apply {
                isHorizontal = false
                layoutID = R.layout.volume_osd_vertical
                backgroundID = R.drawable.layout_dimmer_vertical
                backgroundRotation = 180f
            },
            OSDPosition("rightBottom","Right Bottom", Gravity.BOTTOM or Gravity.END).apply {
                isHorizontal = false
                layoutID = R.layout.volume_osd_vertical
                backgroundID = R.drawable.layout_dimmer_vertical
                backgroundRotation = 180f
            },

            OSDPosition("bottomLeft", "Bottom Left", Gravity.BOTTOM or Gravity.START).apply {
                isHorizontal = true
                layoutID = R.layout.volume_osd_horizontal
                backgroundID = R.drawable.layout_dimmer_horizontal
                backgroundRotation = 180f
            },
            OSDPosition("bottomCenter", "Bottom", Gravity.BOTTOM or Gravity.CENTER).apply {
                isHorizontal = true
                layoutID = R.layout.volume_osd_horizontal
                backgroundID = R.drawable.layout_dimmer_horizontal
                backgroundRotation = 180f
            },
            OSDPosition("bottomRight", "Bottom Right", Gravity.BOTTOM or Gravity.END).apply {
                isHorizontal = true
                layoutID = R.layout.volume_osd_horizontal
                backgroundID = R.drawable.layout_dimmer_horizontal
                backgroundRotation = 180f
            },
        )
    }
}