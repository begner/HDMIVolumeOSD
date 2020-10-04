package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.Window
import android.widget.*

class PopupActivitySettingsGlobal : PopupActivity() {

    val Duration: EditText = findViewById(R.id.duration)
    val LimitOnHDMI: com.google.android.material.switchmaterial.SwitchMaterial = findViewById(R.id.limit_on_hdmi)

    lateinit var settingsGlobal: SettingsGlobal

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_global)

        settingsGlobal = SettingsGlobal(applicationContext)

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