package com.begner.hdmivolumeosd

import androidx.constraintlayout.widget.ConstraintSet

data class OSDPosition (
    var key: String = "",
    var label: String = "",
    var gravity: Int = 0,
) {
    var layoutRotation: Float = 0f
    var layoutRotationX: Float = 0f
    var layoutRotationY: Float = 0f
    var backgroundRotation: Float = 0f
    var isHorizontal: Boolean = true
    var displayConstraintX: Int = ConstraintSet.LEFT
    var displayConstraintY: Int = ConstraintSet.TOP
}