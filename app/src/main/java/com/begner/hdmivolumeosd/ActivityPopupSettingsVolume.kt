package com.begner.hdmivolumeosd

import android.os.Bundle
import android.widget.*

class ActivityPopupSettingsVolume : ActivityPopup() {

    lateinit var Position: Spinner
    lateinit var Size: EditText
    lateinit var Padding: EditText
    
    lateinit var settingsVolume: SettingsVolume

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_volume)
        HideKeyboardOnFocus(getWindow().getDecorView().getRootView(), this)

        settingsVolume = SettingsVolume(applicationContext)

        Position = findViewById<Spinner>(R.id.position);
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, OSDPositionsVolume().getLabelArray())
        Position.setAdapter(arrayAdapter);

        Size = findViewById<EditText>(R.id.size)
        Padding = findViewById<EditText>(R.id.padding)

        fill()
    }

    private fun fill() {
        Position.setSelection(OSDPositionsVolume().getIndexByKey(settingsVolume.getPosition()))
        Size.setText(settingsVolume.getSize().toString())
        Padding.setText(settingsVolume.getPadding().toString())
    }

    override fun save() {
        settingsVolume.SaveSettings(
            OSDPositionsVolume().getByIndex(Position.selectedItemPosition).key,
            Size.getText().toString().toInt(),
            Padding.getText().toString().toInt(),
        )
    }
}