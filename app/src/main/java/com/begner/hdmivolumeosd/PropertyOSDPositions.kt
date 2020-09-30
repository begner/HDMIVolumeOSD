package com.begner.hdmivolumeosd

import android.view.Gravity

class PropertyOSDPositions() {

    private val availablePositions = listOf(
        PropertyOSDPosition("topLeft", "Top Left", Gravity.TOP or Gravity.START),
        PropertyOSDPosition("topCenter", "Top", Gravity.TOP or Gravity.CENTER),
        PropertyOSDPosition("topRight","Top Right", Gravity.TOP or Gravity.END),

        PropertyOSDPosition("rightTop","Right Top", Gravity.TOP or Gravity.END, false),
        PropertyOSDPosition("rightCenter","Right", Gravity.CENTER or Gravity.END, false),
        PropertyOSDPosition("rightBottom","Right Bottom", Gravity.BOTTOM or Gravity.END, false),

        PropertyOSDPosition("bottomLeft", "Bottom Left", Gravity.BOTTOM or Gravity.START),
        PropertyOSDPosition("bottomCenter", "Bottom", Gravity.BOTTOM or Gravity.CENTER),
        PropertyOSDPosition("bottomRight", "Bottom Right", Gravity.BOTTOM or Gravity.END),

        PropertyOSDPosition("leftTop","Left Top", Gravity.TOP or Gravity.START, false),
        PropertyOSDPosition("leftCenter","Left", Gravity.CENTER or Gravity.START, false),
        PropertyOSDPosition("leftBottom","Left Bottom", Gravity.BOTTOM or Gravity.START, false)

    )

    public fun getByIndex(index: Int) : PropertyOSDPosition {
        return availablePositions[index]
    }

    public fun getIndexByKey(searchKey: String):Int {
        val index = availablePositions.indexOfFirst { it.key.equals(searchKey) }
        return index
    }

    public fun getPositionByKey(searchKey: String):PropertyOSDPosition {
        var prop = availablePositions.find { it.key.equals(searchKey) }
        if (prop == null) {
            prop = PropertyOSDPosition("default", "Default", Gravity.TOP or Gravity.END)
        }
        return prop
    }

    public fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availablePositions.map { it.label };
        return keyMap.toTypedArray()
    }

}
