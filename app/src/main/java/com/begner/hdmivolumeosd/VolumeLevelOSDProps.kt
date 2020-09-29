package nl.rogro82.pipup

data class VolumeLevelOSDProps(
    val curVolume: Int = 0,
    val maxVolume: Int = 60,
    val curTemp: Float = 0f
)