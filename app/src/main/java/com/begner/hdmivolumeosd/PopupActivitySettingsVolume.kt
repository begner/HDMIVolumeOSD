package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.Window
import android.widget.*

class PopupActivitySettingsVolume : PopupActivity() {

    val Position: Spinner = findViewById(R.id.position)
    val Size: EditText = findViewById(R.id.size)
    val Padding: EditText = findViewById(R.id.padding)
    
    lateinit var settingsVolume: SettingsVolume

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_volume)

        settingsVolume = SettingsVolume(applicationContext)

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, OSDPositionsVolume().getLabelArray())
        Position.setAdapter(arrayAdapter);

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