package com.begner.hdmivolumeosd

data class OSDMapping (
    var key: String = "",
    var label: String = "",
) {
    var mappingClass = VolumeMapping()
}