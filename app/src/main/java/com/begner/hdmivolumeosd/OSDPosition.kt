package com.begner.hdmivolumeosd

data class OSDPosition (
    val key: String = "",
    val label: String = "",
    val gravity: Int = 0,
    val dimmerRotation: Float = 0f,
    val isHorizontal: Boolean = true
)