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
        var value = getValueAsInt("MaxTemp", 75)
        if (value < 1) {
            value = 1
        }
        return value
    }

    fun getMinTemp(): Int {
        loadPreferences()
        var value = getValueAsInt("MinTemp", 20)
        if (value < 0) {
            value = 0
        }
        return value
    }

    fun getPadding(): Int {
        loadPreferences()
        var value = getValueAsInt("Padding", 5)
        if (value < 0) {
            value = 0
        }
        return value
    }

    fun getMQTTServer(): String {
        loadPreferences()
        return getValueAsString("Server", "")
    }

    fun getMQTTTopic(): String {
        loadPreferences()
        return getValueAsString("Topic", "")
    }

    fun getMQTTUser(): String {
        loadPreferences()
        return getValueAsString("User", "")
    }

    fun getMQTTPassword(): String {
        loadPreferences()
        return getValueAsString("Pass", "")
    }

    fun getMQTTClientId(): String {
        loadPreferences()
        return getValueAsString("ClientId", "")
    }

    fun saveSettings(
        active: Boolean,
        position: String,
        style: String,
        minTemp: Int,
        maxTemp: Int,
        server: String,
        topic: String,
        user: String,
        passwd: String,
        clientId: String,
        padding: Int,
        ) {
        loadPreferences()
        val editor: SharedPreferences.Editor = sharedpreferences.edit()
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
        editor.putInt("Padding", padding)
        editor.apply()
    }


}