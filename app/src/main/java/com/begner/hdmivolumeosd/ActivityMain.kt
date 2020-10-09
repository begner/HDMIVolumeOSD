package com.begner.hdmivolumeosd

import android.content.Context
import android.content.Intent
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.media.AudioManager.AudioPlaybackCallback
import android.media.AudioPlaybackConfiguration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.canDrawOverlays
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.GuardedBy

class ActivityMain : ActivityGlobal() {

    lateinit var mainServiceIntent: Intent

    val REQUEST_CODE_SETTINGS_GLOBAL = 1
    val REQUEST_CODE_SETTINGS_VOLUME = 2
    val REQUEST_CODE_SETTINGS_TEMPERATURE = 3

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
        startActivityForResult(intent, REQUEST_CODE_SETTINGS_GLOBAL);
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsVolume(view: View) {
        val intent = Intent(this, ActivityPopupSettingsVolume::class.java)
        startActivityForResult(intent, REQUEST_CODE_SETTINGS_VOLUME);
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsTemperature(view: View) {
        val intent = Intent(this, ActivityPopupSettingsTemperature::class.java)
        startActivityForResult(intent, REQUEST_CODE_SETTINGS_TEMPERATURE);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SETTINGS_GLOBAL ||
            requestCode == REQUEST_CODE_SETTINGS_VOLUME ||
            requestCode == REQUEST_CODE_SETTINGS_TEMPERATURE) {
            if (resultCode == RESULT_OK) {
                stopService(mainServiceIntent)
                startForegroundService(mainServiceIntent)
            }
            if (resultCode == RESULT_CANCELED) {
                // Nothing to do here
            }
        }
    }
}