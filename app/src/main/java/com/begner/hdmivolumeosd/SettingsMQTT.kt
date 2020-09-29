package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class SettingsMQTT(applicationContext: Context) : Settings() {

    init {
        context = applicationContext
        preferenceId = "mqtt"
    }

    fun getMQTTActive(): Boolean {
        loadPreferences()
        return getValueAsBoolean("MQTTActive", false)
    }

    fun getMQTTServer(): String? {
        loadPreferences()
        return getValueAsString("MQTTServer", "")
    }

    fun getMQTTTopic(): String? {
        loadPreferences()
        return getValueAsString("MQTTTopic", "")
    }

    fun getMQTTUser(): String? {
        loadPreferences()
        return getValueAsString("MQTTUser", "")
    }

    fun getMQTTPassword(): String? {
        loadPreferences()
        return getValueAsString("MQTTPass", "")
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


}