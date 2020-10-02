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
        return getValueAsBoolean("Active", false)
    }

    fun getPosition(): String {
        loadPreferences()
        return getValueAsString("Position", "bottomRight")
    }

    fun getMQTTServer(): String? {
        loadPreferences()
        return getValueAsString("Server", "")
    }

    fun getMQTTTopic(): String? {
        loadPreferences()
        return getValueAsString("Topic", "")
    }

    fun getMQTTUser(): String? {
        loadPreferences()
        return getValueAsString("User", "")
    }

    fun getMQTTPassword(): String? {
        loadPreferences()
        return getValueAsString("Pass", "")
    }

    fun SaveSettings(
        active: Boolean,
        position: String,
        server: String,
        topic: String,
        user: String,
        passwd: String
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putBoolean("Active", active)
        editor.putString("Position", position)
        editor.putString("Server", server)
        editor.putString("Topic", topic)
        editor.putString("User", user)
        editor.putString("Pass", passwd)
        editor.commit()
    }


}