package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*


class PopupActivitySettingsTemperature : PopupActivity() {

    lateinit var Active: Switch
    lateinit var Position: Spinner
    lateinit var MaxTemp: TextView
    lateinit var Server: TextView
    lateinit var Topic: TextView
    lateinit var User: TextView
    lateinit var Password: TextView

    lateinit var TabContentMain: ScrollView
    lateinit var TabContentMqtt: ScrollView

    lateinit var TabButtonMain: Button
    lateinit var TabButtonMqtt: Button

    lateinit var settingsTemperature: SettingsTemperature

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_temperature)

        settingsTemperature = SettingsTemperature(applicationContext)

        Active = findViewById(R.id.active);

        Position = findViewById(R.id.position);
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            OSDPositionsTemperature().getLabelArray()
        )
        Position.setAdapter(arrayAdapter);

        MaxTemp = findViewById(R.id.max_temp)
        Server = findViewById(R.id.server);
        Topic = findViewById(R.id.topic);
        User = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        TabContentMain = findViewById(R.id.main_settings)
        TabContentMqtt = findViewById(R.id.mqtt_settings)
        TabButtonMain = findViewById(R.id.tab_btn_main)
        TabButtonMqtt = findViewById(R.id.tab_btn_mqtt)
        activateTabMain()
        fill()
    }

    private fun fill() {
        Active.setChecked(settingsTemperature.getMQTTActive())
        Position.setSelection(OSDPositionsTemperature().getIndexByKey(settingsTemperature.getPosition()))
        MaxTemp.setText(settingsTemperature.getMaxTemp().toString())
        Server.setText(settingsTemperature.getMQTTServer())
        Topic.setText(settingsTemperature.getMQTTTopic())
        User.setText(settingsTemperature.getMQTTUser())
        Password.setText(settingsTemperature.getMQTTPassword())
    }

    override fun save() {
        settingsTemperature.SaveSettings(
            Active.isChecked(),
            OSDPositionsTemperature().getByIndex(Position.selectedItemPosition).key,
            MaxTemp.getText().toString().toInt(),
            Server.getText().toString(),
            Topic.getText().toString(),
            User.getText().toString(),
            Password.getText().toString()
        )
    }

    @Suppress("UNUSED_PARAMETER")
    fun activateTabMain(view: View? = null) {
        TabContentMain.visibility = ScrollView.VISIBLE
        TabContentMqtt.visibility = ScrollView.GONE
        TabButtonMain.isSelected = true
        TabButtonMqtt.isSelected = false
    }

    @Suppress("UNUSED_PARAMETER")
    fun activateTabMqtt(view: View? = null) {
        TabContentMain.visibility = ScrollView.GONE
        TabContentMqtt.visibility = ScrollView.VISIBLE
        TabButtonMain.isSelected = false
        TabButtonMqtt.isSelected = true
    }
}