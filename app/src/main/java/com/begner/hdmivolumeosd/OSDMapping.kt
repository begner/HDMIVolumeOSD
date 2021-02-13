package com.begner.hdmivolumeosd

import androidx.constraintlayout.widget.ConstraintSet

data class OSDMapping (
    var key: String = "",
    var label: String = "",
) {
    var mappingClass = VolumeMapping()
}