package com.begner.hdmivolumeosd

import android.content.Context
import android.content.SharedPreferences

class SettingsTemperature(applicationContext: Context) : Settings() {

    init {
        context = applicationContext
        preferenceId = "SettingsTemperature"
    }

    fun getMQTTActive(): Boolean {
        loadPreferences()
        return getValueAsBoolean("Active", false)
    }

    fun getPosition(): String {
        loadPreferences()
        return getValueAsString("Position", "bottomRight")
    }

    fun getStyle(): String {
        loadPreferences()
        return getValueAsString("Style", "android")
    }

    fun getMaxTemp(): Int {
        loadPreferences()
        var size = getValueAsInt("MaxTemp", 75)
        if (size < 1) {
            size = 1
        }
        return size
    }

    fun getMinTemp(): Int {
        loadPreferences()
        var size = getValueAsInt("MinTemp", 20)
        if (size < 0) {
            size = 0
        }
        return size
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

    fun getMQTTClientId(): String? {
        loadPreferences()
        return getValueAsString("ClientId", "")
    }

    fun SaveSettings(
        active: Boolean,
        position: String,
        style: String,
        minTemp: Int,
        maxTemp: Int,
        server: String,
        topic: String,
        user: String,
        passwd: String,
        clientId: String
    ) {
        loadPreferences()
        var editor: SharedPreferences.Editor = sharedpreferences.edit()
        editor.putBoolean("Active", active)
        editor.putString("Position", position)
        editor.putString("Style", style)
        editor.putInt("MinTemp", minTemp)
        editor.putInt("MaxTemp", maxTemp)
        editor.putString("Server", server)
        editor.putString("Topic", topic)
        editor.putString("User", user)
        editor.putString("Pass", passwd)
        editor.putString("ClientId", clientId)
        editor.commit()
    }


}