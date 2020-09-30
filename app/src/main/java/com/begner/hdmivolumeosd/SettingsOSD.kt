package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences
import java.time.Duration

class SettingsOSD(applicationContext: Context) : Settings() {

    init {
        context = applicationContext
        preferenceId = "volume"
    }

    fun getPosition(): String {
        loadPreferences()
        return getValueAsString("Position", "topRight")
    }

    fun getDuration(): Int {
        loadPreferences()
        var dur = getValueAsInt("Duration", 3)
        if (dur < 0) {
            dur = 0
        }
        return dur
    }

    fun getSize(): Int {
        loadPreferences()
        var size = getValueAsInt("Size", 80)
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
        var padding = getValueAsInt("Padding", 20)
        if (padding < 0) {
            padding = 0
        }
        return padding
    }

    fun getLimitOnHDMI() : Boolean {
        loadPreferences()
        return getValueAsBoolean("LimitOnHDMI", true)
    }

    fun SaveSettings(
        position: String,
        duration: Int,
        size: Int,
        padding: Int,
        limitOnHDMI: Boolean
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putString("Position", position)
        editor.putInt("Duration", duration)
        editor.putInt("Size", size)
        editor.putInt("Padding", padding)
        editor.putBoolean("LimitOnHDMI", limitOnHDMI)
        editor.commit()
    }


}