package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*

class PopupActivitySettingsVolume : PopupActivity() {

    lateinit var settingsVolumePosition: Spinner
    lateinit var settingsVolumeDuration: EditText
    lateinit var settingsVolumeLimitOnHDMI: Switch

    lateinit var settingsVolume: SettingsVolume

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_volume)

        settingsVolumePosition = findViewById<Spinner>(R.id.settings_volume_position);
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PropertyOSDPositions().getLabelArray())
        settingsVolumePosition.setAdapter(arrayAdapter);

        settingsVolumeDuration = findViewById<EditText>(R.id.settings_volume_duration)
        settingsVolumeLimitOnHDMI = findViewById<Switch>(R.id.settings_limit_on_hdmi)
        settingsVolume = SettingsVolume(applicationContext)

        fill()
    }

    private fun fill() {
        settingsVolumePosition.setSelection(PropertyOSDPositions().getIndexByKey(settingsVolume.getPosition()))
        settingsVolumeDuration.setText(settingsVolume.getDuration().toString())
        settingsVolumeLimitOnHDMI.setChecked(settingsVolume.getLimitOnHDMI())
    }

    fun ActionSave(view: View?) {
        settingsVolume.SaveSettings(
            PropertyOSDPositions().getByIndex(settingsVolumePosition.selectedItemPosition).key,
            settingsVolumeDuration.getText().toString().toInt(),
            settingsVolumeLimitOnHDMI.isChecked()
        )
        setResultAndFinish(true)
    }


}