package com.begner.hdmivolumeosd

import android.animation.Animator
import android.animation.ObjectAnimator


class OSDMappingVolume() : OSDMappings() {
    init {
        availableMappings = listOf(
            OSDMapping("default","1:1 Android Volume").apply {

            },
            OSDMapping("sony_htrt3","Sony HTRT3").apply {
                mappingClass = VolumeMappingSonyHtRt3()
            }
        )
    }
}