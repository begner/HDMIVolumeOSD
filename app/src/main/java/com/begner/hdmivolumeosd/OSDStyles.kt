package com.begner.hdmivolumeosd

abstract class OSDStyles {

    lateinit var availableStyles : List<OSDStyle>

    fun getByIndex(index: Int) : OSDStyle {
        return availableStyles[index]
    }

    fun getIndexByKey(searchKey: String):Int {
        return availableStyles.indexOfFirst { it.key == searchKey }
    }

    fun getPositionByKey(searchKey: String):OSDStyle {
        var prop = availableStyles.find { it.key == searchKey }
        if (prop == null) {
            prop = OSDStyle("default", "Default")
        }
        return prop
    }

    fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availableStyles.map { it.label }
        return keyMap.toTypedArray()
    }


}