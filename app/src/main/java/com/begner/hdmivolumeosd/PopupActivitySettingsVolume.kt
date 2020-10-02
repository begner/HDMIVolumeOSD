package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.Window
import android.widget.*

class PopupActivitySettingsVolume : PopupActivity() {

    lateinit var Position: Spinner
    lateinit var Size: EditText
    lateinit var LimitOnHDMI: Switch
    lateinit var Padding: EditText
    
    lateinit var settingsVolume: SettingsVolume

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_volume)

        settingsVolume = SettingsVolume(applicationContext)

        Position = findViewById<Spinner>(R.id.position);
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, OSDPositionsVolume().getLabelArray())
        Position.setAdapter(arrayAdapter);

        Size = findViewById<EditText>(R.id.size)
        Padding = findViewById<EditText>(R.id.padding)
        LimitOnHDMI = findViewById<Switch>(R.id.limit_on_hdmi)
        
        fill()
    }

    private fun fill() {
        Position.setSelection(OSDPositionsVolume().getIndexByKey(settingsVolume.getPosition()))
        Size.setText(settingsVolume.getSize().toString())
        Padding.setText(settingsVolume.getPadding().toString())
        LimitOnHDMI.setChecked(settingsVolume.getLimitOnHDMI())
    }

    override fun save() {
        settingsVolume.SaveSettings(
            OSDPositionsVolume().getByIndex(Position.selectedItemPosition).key,
            Size.getText().toString().toInt(),
            Padding.getText().toString().toInt(),
            LimitOnHDMI.isChecked()
        )
    }


}