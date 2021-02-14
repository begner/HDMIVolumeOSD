package com.begner.hdmivolumeosd

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.canDrawOverlays
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class ActivityMain : ActivityGlobal() {

    private lateinit var mainServiceIntent: Intent

    private val requestCodeSettingGlobal = 1
    private val requestCodeSettingVolume = 2
    private val requestCodeSettingTemperature = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val permissionRequired = findViewById<LinearLayout>(R.id.main_permission_required)
        if (canDrawOverlays(applicationContext)) {
            permissionRequired.visibility = LinearLayout.GONE
        } else {
            permissionRequired.visibility = LinearLayout.VISIBLE
        }

        val buildVersion = findViewById<TextView>(R.id.main_build_version)
        buildVersion.text = getString(
            R.string.main_version_tag,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )

        mainServiceIntent = Intent(this, MainService::class.java)
        startForegroundService(mainServiceIntent)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsGlobal(view: View) {
        val intent = Intent(this, ActivityPopupSettingsGlobal::class.java)
        startActivityForResult(intent, requestCodeSettingGlobal)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsVolume(view: View) {
        val intent = Intent(this, ActivityPopupSettingsVolume::class.java)
        startActivityForResult(intent, requestCodeSettingVolume)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsTemperature(view: View) {
        val intent = Intent(this, ActivityPopupSettingsTemperature::class.java)
        startActivityForResult(intent, requestCodeSettingTemperature)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeSettingGlobal ||
            requestCode == requestCodeSettingVolume ||
            requestCode == requestCodeSettingTemperature) {
            if (resultCode == RESULT_OK) {
                stopService(mainServiceIntent)
                startForegroundService(mainServiceIntent)
            }
        }
    }
}