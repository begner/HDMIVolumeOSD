package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView

class PopupActivitySettingsTemperature : PopupActivity() {

    lateinit var Active: Switch
    lateinit var Position: Spinner
    lateinit var MaxTemp: TextView
    lateinit var Server: TextView
    lateinit var Topic: TextView
    lateinit var User: TextView
    lateinit var Password: TextView

    lateinit var settingsTemperature: SettingsTemperature

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_temperature)

        settingsTemperature = SettingsTemperature(applicationContext)

        Active = findViewById(R.id.active);

        Position = findViewById<Spinner>(R.id.position);
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, OSDPositionsTemperature().getLabelArray())
        Position.setAdapter(arrayAdapter);

        MaxTemp = findViewById(R.id.max_temp)
        Server = findViewById(R.id.server);
        Topic = findViewById(R.id.topic);
        User = findViewById(R.id.username);
        Password = findViewById(R.id.password);

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
            Password.getText().toString())
    }
}