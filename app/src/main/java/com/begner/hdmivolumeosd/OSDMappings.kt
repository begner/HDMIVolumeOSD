package com.begner.hdmivolumeosd

abstract class OSDMappings() {

    lateinit var availableMappings : List<OSDMapping>

    public fun getByIndex(index: Int) : OSDMapping {
        return availableMappings[index]
    }

    public fun getIndexByKey(searchKey: String):Int {
        val index = availableMappings.indexOfFirst { it.key.equals(searchKey) }
        return index
    }

    public fun getPositionByKey(searchKey: String):OSDMapping {
        var prop = availableMappings.find { it.key.equals(searchKey) }
        if (prop == null) {
            prop = OSDMapping("default", "Default")
        }
        return prop
    }

    public fun getLabelArray(): Array<String> {
        val keyMap: List<String> = availableMappings.map { it.label };
        return keyMap.toTypedArray()
    }

}
