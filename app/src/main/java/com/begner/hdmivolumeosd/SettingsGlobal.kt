package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class SettingsGlobal(applicationContext: Context) : Settings() {

    init {
        context = applicationContext
        preferenceId = "SettingsGlobal"
    }

    fun getDuration(): Int {
        loadPreferences()
        var dur = getValueAsInt("Duration", 5)
        if (dur < 0) {
            dur = 0
        }
        return dur
    }

    fun getLimitOnHDMI() : Boolean {
        loadPreferences()
        return getValueAsBoolean("LimitOnHDMI", true)
    }

    fun saveSettings(
        duration: Int,
        limitOnHDMI: Boolean
    ) {
        loadPreferences()
        val editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putInt("Duration", duration)
        editor.putBoolean("LimitOnHDMI", limitOnHDMI)
        editor.apply()
    }

}