package nl.rogro82.pipup

data class VolumeLevelOSDProps(
    val curVolume: Int = 0,
    val maxVolume: Int = 60,
    val curTemp: Float = 0f,
    val duration: Int = 3,
    val position: Position = Position.TopRight
) {
    enum class Position(index: Int) {
        TopRight(0),
        TopLeft(1),
        BottomRight(2),
        BottomLeft(3),
        Center(4)
    }
}