package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

abstract class Settings {

    lateinit var sharedpreferences: SharedPreferences
    lateinit var context: Context
    var preferenceId: String = ""

    fun loadPreferences() {
        sharedpreferences = context.getSharedPreferences(preferenceId, Context.MODE_PRIVATE)
    }

    fun getValueAsString(ident: String, default: String) : String {
        if (sharedpreferences.contains(ident)) {
            val pref = sharedpreferences.getString(ident, default)
            if (pref !== null) {
                return pref.toString()
            }
        }
        return default
    }

    fun getValueAsBoolean(ident: String, default: Boolean) : Boolean {
        if (sharedpreferences.contains(ident)) {
            val pref = sharedpreferences.getBoolean(ident, default)
            return pref
        }
        return default
    }

    fun getValueAsInt(ident: String, default: Int) : Int {
        var pref = default
        if (sharedpreferences.contains(ident)) {
            pref = sharedpreferences.getInt(ident, default)
        }
        return pref
    }

}