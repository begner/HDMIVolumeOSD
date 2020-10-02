package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences
import java.time.Duration

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

    fun SaveSettings(
        duration: Int,
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putInt("Duration", duration)
        editor.commit()
    }


}