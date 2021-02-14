package com.begner.hdmivolumeosd

import kotlin.math.floor

class VolumeMappingSonyHtRt3 : VolumeMapping() {

    override fun mapVolume(volume: Int, maxVolume: Int): Int {
        val versatz = floor(volume.toDouble() / 6.toDouble()).toInt()
        return volume - versatz
    }
}