package com.begner.hdmivolumeosd

import android.animation.Animator

data class OSDStyle (
    var key: String = "",
    var label: String = ""
) {
    var layoutID: Int? = null
    var layoutIDVertical: Int = R.layout.volume_osd_vertical
    var layoutIDHorizontal: Int = R.layout.volume_osd_horizontal
    var backgroundID: Int? = null
    var backgroundIDVertical: Int? = R.drawable.layout_dimmer_vertical
    var backgroundIDHorizontal: Int? = R.drawable.layout_dimmer_horizontal
    var animationDuration: Long = 0
    var animationDelay: Long = 0
    var animationClass: StyleAnimation = StyleAnimation()

    fun getLayout(isHorizontal : Boolean) : Int {
        if (layoutID != null) {
            return layoutID!!
        }
        if (isHorizontal) {
            return layoutIDHorizontal
        } else {
            return layoutIDVertical
        }
    }

    fun getBackground(isHorizontal : Boolean) : Int? {
        if (backgroundID != null) {
            return backgroundID!!
        }
        if (isHorizontal) {
            return backgroundIDHorizontal
        } else {
            return backgroundIDVertical
        }
    }
}