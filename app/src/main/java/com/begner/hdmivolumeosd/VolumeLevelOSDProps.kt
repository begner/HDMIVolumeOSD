package nl.rogro82.pipup

data class VolumeLevelOSDProps(
    val curVolume: Int = 0,
    val maxVolume: Int = 10,
    val duration: Int = DEFAULT_DURATION,
    val position: Position = DEFAULT_POSITION,
    val backgroundColor: String = DEFAULT_BACKGROUND_COLOR
) {
    enum class Position(index: Int) {
        TopRight(0),
        TopLeft(1),
        BottomRight(2),
        BottomLeft(3),
        Center(4)
    }
    companion object {
        const val DEFAULT_DURATION: Int = 3
        const val DEFAULT_BACKGROUND_COLOR = "#88000000"
        val DEFAULT_POSITION: Position = Position.TopRight
    }
}