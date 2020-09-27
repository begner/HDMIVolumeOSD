package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class MQTTSettings(applicationContext: Context) {

    lateinit var sharedpreferences: SharedPreferences
    var context: Context = applicationContext
    var preferenceId: String = "mqtt"

    fun Settings(applicationContext: Context) {
        loadPreferences()
    }

    fun loadPreferences() {
        sharedpreferences = context.getSharedPreferences(preferenceId, Context.MODE_PRIVATE);
    }

    fun getMQTTServer(): String? {
        loadPreferences()
        return getValueAsString("MQTTServer")
    }

    fun getMQTTTopic(): String? {
        loadPreferences()
        return getValueAsString("MQTTTopic")
    }

    fun getMQTTUser(): String? {
        loadPreferences()
        return getValueAsString("MQTTUser")
    }

    fun getMQTTPassword(): String? {
        loadPreferences()
        return getValueAsString("MQTTPass")
    }

    fun getMQTTActive(): Boolean {
        loadPreferences()
        return getValueAsBoolean("MQTTActive")
    }

    fun SaveSettings(
        active: Boolean,
        server: String,
        topic: String,
        user: String,
        passwd: String
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putBoolean("MQTTActive", active)
        editor.putString("MQTTServer", server)
        editor.putString("MQTTTopic", topic)
        editor.putString("MQTTUser", user)
        editor.putString("MQTTPass", passwd)
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