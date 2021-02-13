package com.begner.hdmivolumeosd

class VolumeMappingSonyHtRt3 : VolumeMapping() {

    override public fun mapVolume(volume: Int, maxVolume: Int): Int {
        var versatz = Math.floor(volume.toDouble() / 6.toDouble()).toInt()
        return volume - versatz
    }
}