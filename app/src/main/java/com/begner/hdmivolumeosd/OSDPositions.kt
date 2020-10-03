package com.begner.hdmivolumeosd

import android.view.Gravity
import android.widget.ScrollView

abstract class OSDPositions() {

    lateinit var availablePositions : List<OSDPosition>

    public fun getByIndex(index: Int) : OSDPosition {
        return availablePositions[index]
    }

    public fun getIndexByKey(searchKey: String):Int {
        val index = availablePositions.indexOfFirst { it.key.equals(searchKey) }
        return index
    }

    public fun getPositionByKey(searchKey: String):OSDPosition {
        var prop = availablePositions.find { it.key.equals(searchKey) }
        if (prop == null) {
            prop = OSDPosition("default", "Default", Gravity.TOP or Gravity.END)
        }
        return prop
    }

    public fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availablePositions.map { it.label };
        return keyMap.toTypedArray()
    }

}
