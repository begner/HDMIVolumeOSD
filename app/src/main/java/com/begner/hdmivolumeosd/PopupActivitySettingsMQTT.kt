package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Switch
import android.widget.TextView

class PopupActivitySettingsMQTT : PopupActivity() {

    lateinit var Active: Switch
    lateinit var Server: TextView
    lateinit var Topic: TextView
    lateinit var User: TextView
    lateinit var Password: TextView

    lateinit var settingsMQTT: SettingsMQTT

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_mqtt)

        settingsMQTT = SettingsMQTT(applicationContext)

        Active = findViewById(R.id.settings_mmqt_active);
        Server = findViewById(R.id.settings_mqtt_server);
        Topic = findViewById(R.id.settings_mqtt_topic);
        User = findViewById(R.id.settings_mqtt_username);
        Password = findViewById(R.id.settings_mqtt_password);

        fill()
    }

    private fun fill() {
        Active.setChecked(settingsMQTT.getMQTTActive())
        Server.setText(settingsMQTT.getMQTTServer())
        Topic.setText(settingsMQTT.getMQTTTopic())
        User.setText(settingsMQTT.getMQTTUser())
        Password.setText(settingsMQTT.getMQTTPassword())
    }

    override fun save() {
        settingsMQTT.SaveSettings(
            Active.isChecked(),
            Server.getText().toString(),
            Topic.getText().toString(),
            User.getText().toString(),
            Password.getText().toString())
    }
}