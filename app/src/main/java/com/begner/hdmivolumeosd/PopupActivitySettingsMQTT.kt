package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Switch
import android.widget.TextView


class PopupActivitySettingsMQTT : PopupActivity() {

    lateinit var settingsMQTTActive: Switch
    lateinit var settingsMQTTServer: TextView
    lateinit var settingsMQTTTopic: TextView
    lateinit var settingsMQTTUser: TextView
    lateinit var settingsMQTTPassword: TextView

    lateinit var settingsMQTT: SettingsMQTT

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_mqtt)

        settingsMQTTActive = findViewById(R.id.settings_mmqt_active);
        settingsMQTTServer = findViewById(R.id.settings_mqtt_server);
        settingsMQTTTopic = findViewById(R.id.settings_mqtt_topic);
        settingsMQTTUser = findViewById(R.id.settings_mqtt_username);
        settingsMQTTPassword = findViewById(R.id.settings_mqtt_password);

        settingsMQTT = SettingsMQTT(applicationContext)

        fill()
    }

    private fun fill() {
        settingsMQTTActive.setChecked(settingsMQTT.getMQTTActive())
        settingsMQTTServer.setText(settingsMQTT.getMQTTServer())
        settingsMQTTTopic.setText(settingsMQTT.getMQTTTopic())
        settingsMQTTUser.setText(settingsMQTT.getMQTTUser())
        settingsMQTTPassword.setText(settingsMQTT.getMQTTPassword())
    }

    fun ActionSave(view: View?) {
        settingsMQTT.SaveSettings(
            settingsMQTTActive.isChecked(),
            settingsMQTTServer.getText().toString(),
            settingsMQTTTopic.getText().toString(),
            settingsMQTTUser.getText().toString(),
            settingsMQTTPassword.getText().toString())
        setResultAndFinish(true)
    }


}