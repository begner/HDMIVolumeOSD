package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.Window
import android.widget.*

class PopupActivitySettingsGlobal : PopupActivity() {

    lateinit var Duration: EditText
    lateinit var LimitOnHDMI: com.google.android.material.switchmaterial.SwitchMaterial

    lateinit var settingsGlobal: SettingsGlobal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_global)

        settingsGlobal = SettingsGlobal(applicationContext)

        Duration = findViewById(R.id.duration)
        LimitOnHDMI = findViewById(R.id.limit_on_hdmi)

        fill()
    }

    private fun fill() {
        Duration.setText(settingsGlobal.getDuration().toString())
        LimitOnHDMI.setChecked(settingsGlobal.getLimitOnHDMI())

    }

    override fun save() {
        settingsGlobal.SaveSettings(
            Duration.getText().toString().toInt(),
            LimitOnHDMI.isChecked()
        )
    }


}