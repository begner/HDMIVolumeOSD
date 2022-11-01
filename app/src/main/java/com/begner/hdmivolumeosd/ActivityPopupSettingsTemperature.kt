package com.begner.hdmivolumeosd

import android.os.Bundle
import android.widget.*

class ActivityPopupSettingsTemperature : ActivityPopup() {

    lateinit var Active: com.google.android.material.switchmaterial.SwitchMaterial
    lateinit var Position: Spinner
    lateinit var Style: Spinner
    lateinit var MaxTemp: TextView
    lateinit var MinTemp: TextView
    lateinit var Server: TextView
    lateinit var Topic: TextView
    lateinit var User: TextView
    lateinit var Password: TextView
    lateinit var ClientId: TextView
    lateinit var Padding: EditText

    lateinit var settingsTemperature: SettingsTemperature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_temperature)
        hideKeyboardOnFocus(getWindow().getDecorView().getRootView(), this)

        settingsTemperature = SettingsTemperature(applicationContext)

        Active = findViewById(R.id.active);

        Position = findViewById(R.id.position);
        val positionArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            OSDPositionsTemperature().getLabelArray()
        )
        Position.setAdapter(positionArrayAdapter);

        Style = findViewById(R.id.style);
        val styleArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            OSDStylesTemperature().getLabelArray()
        )
        Style.setAdapter(styleArrayAdapter);

        Padding = findViewById(R.id.padding)
        MinTemp = findViewById(R.id.min_temp)
        MaxTemp = findViewById(R.id.max_temp)
        Server = findViewById(R.id.server);
        Topic = findViewById(R.id.topic);
        User = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        ClientId = findViewById(R.id.clientId);

        tabList = listOf(
            TabData("main", "Main", findViewById(R.id.main_settings)),
            TabData("apperence", "Apperence", findViewById(R.id.apperence_settings)),
            TabData("mqtt", "Mqtt", findViewById(R.id.mqtt_settings)),
            TabData("mqtt2", "Mqtt2", findViewById(R.id.mqtt_settings2)),
            TabData("extra", "Extras", findViewById(R.id.extra_settings)),
        )

        navigationTab = findViewById(R.id.navigationTab)
        navigationTab.addOnTabSelectedListener(this)
        tabList.forEach {
            navigationTab.addTab(navigationTab.newTab().setText(it.label))
        }

        fill()
    }

    private fun fill() {
        Active.setChecked(settingsTemperature.getMQTTActive())
        Position.setSelection(OSDPositionsTemperature().getIndexByKey(settingsTemperature.getPosition()))
        Style.setSelection(OSDStylesTemperature().getIndexByKey(settingsTemperature.getStyle()))
        Padding.setText(settingsTemperature.getPadding().toString())
        MinTemp.setText(settingsTemperature.getMinTemp().toString())
        MaxTemp.setText(settingsTemperature.getMaxTemp().toString())
        Server.setText(settingsTemperature.getMQTTServer())
        Topic.setText(settingsTemperature.getMQTTTopic())
        User.setText(settingsTemperature.getMQTTUser())
        Password.setText(settingsTemperature.getMQTTPassword())
        ClientId.setText(settingsTemperature.getMQTTClientId())
    }

    override fun save() {
        settingsTemperature.saveSettings(
            Active.isChecked(),
            OSDPositionsTemperature().getByIndex(Position.selectedItemPosition).key,
            OSDStylesTemperature().getByIndex(Style.selectedItemPosition).key,
            MinTemp.getText().toString().toInt(),
            MaxTemp.getText().toString().toInt(),
            Server.getText().toString(),
            Topic.getText().toString(),
            User.getText().toString(),
            Password.getText().toString(),
            ClientId.getText().toString(),
            Padding.getText().toString().toInt(),
        )
    }



}

