package com.begner.hdmivolumeosd

abstract class OSDMappings {

    lateinit var availableMappings : List<OSDMapping>

    fun getByIndex(index: Int) : OSDMapping {
        return availableMappings[index]
    }

    fun getIndexByKey(searchKey: String):Int {
        return availableMappings.indexOfFirst { it.key == searchKey }
    }

    fun getPositionByKey(searchKey: String):OSDMapping {
        var prop = availableMappings.find { it.key == searchKey }
        if (prop == null) {
            prop = OSDMapping("default", "Default")
        }
        return prop
    }

    fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availableMappings.map { it.label }
        return keyMap.toTypedArray()
    }

}
