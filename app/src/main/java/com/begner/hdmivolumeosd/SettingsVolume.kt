package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences
import java.time.Duration

class SettingsVolume(applicationContext: Context) : Settings() {

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
        return getValueAsInt("Duration", 3)
    }

    fun getLimitOnHDMI() : Boolean {
        loadPreferences()
        return getValueAsBoolean("LimitOnHDMI", true)
    }

    fun SaveSettings(
        position: String,
        duration: Int,
        limitOnHDMI: Boolean
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putString("Position", position)
        editor.putInt("Duration", duration)
        editor.putBoolean("LimitOnHDMI", limitOnHDMI)
        editor.commit()
    }


}