package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class Settings(applicationContext: Context) {

    lateinit var sharedpreferences: SharedPreferences
    var context: Context = applicationContext
    var preferenceId: String = "myPref"

    fun Settings(applicationContext: Context) {
        loadPreferences()
    }

    fun loadPreferences() {
        sharedpreferences = context.getSharedPreferences(preferenceId, Context.MODE_PRIVATE);
    }

    fun getMQTTServer(): String? {
        loadPreferences()
        return getValue("MQTTServer")
    }

    fun getMQTTTopic(): String? {
        loadPreferences()
        return getValue("MQTTTopic")
    }

    fun getMQTTUser(): String? {
        loadPreferences()
        return getValue("MQTTUser")
    }

    fun getValue(ident: String) : String {
        if (sharedpreferences.contains(ident)) {
            val pref = sharedpreferences.getString(ident, "")
            if (pref !== null) {
                return pref.toString()
            }
        }
        return ""
    }

    fun getMQTTPassword(): String? {
        loadPreferences()
        return getValue("MQTTPass")
    }

    fun SaveSettings(
        server: String?,
        topic: String?,
        user: String?,
        passwd: String?
    ) {
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putString("MQTTServer", server)
        editor.putString("MQTTTopic", topic)
        editor.putString("MQTTUser", user)
        editor.putString("MQTTPass", passwd)
        editor.commit()
    }


}