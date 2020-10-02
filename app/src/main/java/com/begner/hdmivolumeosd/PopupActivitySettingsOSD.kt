package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.Window
import android.widget.*

class PopupActivitySettingsOSD : PopupActivity() {

    lateinit var Position: Spinner
    lateinit var Duration: EditText
    lateinit var Size: EditText
    lateinit var LimitOnHDMI: Switch
    lateinit var Padding: EditText
    
    lateinit var settingsOSD: SettingsOSD

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_volume)

        settingsOSD = SettingsOSD(applicationContext)

        Position = findViewById<Spinner>(R.id.settings_osd_position);
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, OSDPositionsVolume().getLabelArray())
        Position.setAdapter(arrayAdapter);

        Duration = findViewById<EditText>(R.id.settings_osd_duration)
        Size = findViewById<EditText>(R.id.settings_osd_size)
        Padding = findViewById<EditText>(R.id.settings_osd_padding)
        LimitOnHDMI = findViewById<Switch>(R.id.settings_osd_limit_on_hdmi)
        
        fill()
    }

    private fun fill() {
        Position.setSelection(OSDPositionsVolume().getIndexByKey(settingsOSD.getPosition()))
        Duration.setText(settingsOSD.getDuration().toString())
        Size.setText(settingsOSD.getSize().toString())
        Padding.setText(settingsOSD.getPadding().toString())
        LimitOnHDMI.setChecked(settingsOSD.getLimitOnHDMI())
    }

    override fun save() {
        settingsOSD.SaveSettings(
            OSDPositionsVolume().getByIndex(Position.selectedItemPosition).key,
            Duration.getText().toString().toInt(),
            Size.getText().toString().toInt(),
            Padding.getText().toString().toInt(),
            LimitOnHDMI.isChecked()
        )
    }


}