package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class VolumeSettings(applicationContext: Context) {

    lateinit var sharedpreferences: SharedPreferences
    var context: Context = applicationContext
    var preferenceId: String = "volume"

    fun Settings(applicationContext: Context) {
        loadPreferences()
    }

    fun loadPreferences() {
        sharedpreferences = context.getSharedPreferences(preferenceId, Context.MODE_PRIVATE);
    }

    fun getPosition(): String? {
        loadPreferences()
        return getValueAsString("Position")
    }

    fun SaveSettings(
        position: String
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putString("Position", position)
        editor.commit()
    }

    fun getValueAsString(ident: String) : String {
        if (sharedpreferences.contains(ident)) {
            val pref = sharedpreferences.getString(ident, "")
            if (pref !== null) {
                return pref.toString()
            }
        }
        return ""
    }

    fun getValueAsBoolean(ident: String) : Boolean {
        if (sharedpreferences.contains(ident)) {
            val pref = sharedpreferences.getBoolean(ident, false)
            return pref
        }
        return false
    }

}