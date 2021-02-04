package com.begner.hdmivolumeosd

abstract class OSDStyles() {

    lateinit var availableStyles : List<OSDStyle>

    public fun getByIndex(index: Int) : OSDStyle {
        return availableStyles[index]
    }

    public fun getIndexByKey(searchKey: String):Int {
        val index = availableStyles.indexOfFirst { it.key.equals(searchKey) }
        return index
    }

    public fun getPositionByKey(searchKey: String):OSDStyle {
        var prop = availableStyles.find { it.key.equals(searchKey) }
        if (prop == null) {
            prop = OSDStyle("default", "Default")
        }
        return prop
    }

    public fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availableStyles.map { it.label };
        return keyMap.toTypedArray()
    }


}