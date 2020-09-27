package com.begner.hdmivolumeosd

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Switch
import android.widget.TextView


class MQTTSettingsActivity : Activity() {

    lateinit var settingsMQTTActive: Switch
    lateinit var settingsMQTTServer: TextView
    lateinit var settingsMQTTTopic: TextView
    lateinit var settingsMQTTUser: TextView
    lateinit var settingsMQTTPassword: TextView

    lateinit var settings: MQTTSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mqtt_settings)

        settingsMQTTActive = findViewById(R.id.settings_mmqt_active);
        settingsMQTTServer = findViewById(R.id.settings_mqtt_server);
        settingsMQTTTopic = findViewById(R.id.settings_mqtt_topic);
        settingsMQTTUser = findViewById(R.id.settings_mqtt_username);
        settingsMQTTPassword = findViewById(R.id.settings_mqtt_password);

        settings = MQTTSettings(applicationContext)

        fill()
    }

    private fun setResultAndFinish(success: Boolean) {
        setResult(success)
        finish()
    }

    private fun setResult(success: Boolean) {
        val returnIntent = Intent()
        // returnIntent.putExtra("result", "canceled")
        if (success) {
            setResult(RESULT_OK, returnIntent)
        }
        else {
            setResult(RESULT_CANCELED, returnIntent)
        }
    }

    private fun clear(view: View?) {
        settingsMQTTActive.setChecked(false)
        settingsMQTTServer.setText("")
        settingsMQTTTopic.setText("")
        settingsMQTTUser.setText("")
        settingsMQTTPassword.setText("")
    }

    private fun fill() {
        settingsMQTTActive.setChecked(settings.getMQTTActive())
        settingsMQTTServer.setText(settings.getMQTTServer())
        settingsMQTTTopic.setText(settings.getMQTTTopic())
        settingsMQTTUser.setText(settings.getMQTTUser())
        settingsMQTTPassword.setText(settings.getMQTTPassword())
    }

    fun ActionSave(view: View?) {
        settings.SaveSettings(
            settingsMQTTActive.isChecked(),
            settingsMQTTServer.getText().toString(),
            settingsMQTTTopic.getText().toString(),
            settingsMQTTUser.getText().toString(),
            settingsMQTTPassword.getText().toString())
        setResultAndFinish(true)
    }

    fun ActionCancel(view: View?) {
        setResultAndFinish(false)
    }

    override fun onBackPressed() {
        setResultAndFinish(false)
    }


}