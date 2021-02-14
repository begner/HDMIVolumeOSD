package com.begner.hdmivolumeosd

import android.view.Gravity

abstract class OSDPositions {

    lateinit var availablePositions : List<OSDPosition>

    fun getByIndex(index: Int) : OSDPosition {
        return availablePositions[index]
    }

    fun getIndexByKey(searchKey: String):Int {
        return availablePositions.indexOfFirst { it.key == searchKey }
    }

    fun getPositionByKey(searchKey: String):OSDPosition {
        var prop = availablePositions.find { it.key == searchKey }
        if (prop == null) {
            prop = OSDPosition("default", "Default", Gravity.TOP or Gravity.END)
        }
        return prop
    }

    fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availablePositions.map { it.label }
        return keyMap.toTypedArray()
    }

}
