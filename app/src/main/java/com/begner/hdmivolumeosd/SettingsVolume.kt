package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class SettingsVolume(applicationContext: Context) : Settings() {

    init {
        context = applicationContext
        preferenceId = "SettingsVolume"
    }

    fun getPosition(): String {
        loadPreferences()
        return getValueAsString("Position", "leftCenter")
    }

    fun getSize(): Int {
        loadPreferences()
        var size = getValueAsInt("Size", 60)
        if (size < 1) {
            size = 1
        }
        if (size > 100) {
            size = 100
        }
        return size
    }

    fun getPadding() : Int {
        loadPreferences()
        var padding = getValueAsInt("Padding", 5)
        if (padding < 0) {
            padding = 0
        }
        return padding
    }

    fun getMapping() : String {
        loadPreferences()
        return getValueAsString("Mapping", "default")
    }

    fun getStyle() : String {
        loadPreferences()
        return getValueAsString("Style", "android")
    }

    fun saveSettings(
        position: String,
        size: Int,
        padding: Int,
        style: String,
        mapping: String,
    ) {
        loadPreferences()
        val editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putString("Position", position)
        editor.putInt("Size", size)
        editor.putInt("Padding", padding)
        editor.putString("Style", style)
        editor.putString("Mapping", mapping)
        editor.apply()
    }


}