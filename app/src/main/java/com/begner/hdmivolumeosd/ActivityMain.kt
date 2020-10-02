/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.begner.hdmivolumeosd

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.canDrawOverlays
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.begner.hdmivolumeosd.BuildConfig;


class ActivityMain : Activity() {

    lateinit var mainServiceIntent: Intent

    val REQUEST_CODE_SETTINGS_MQTT = 1
    val REQUEST_CODE_SETTINGS_VOLUME = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val permissionRequired = findViewById<LinearLayout>(R.id.main_permission_required)
        if (canDrawOverlays(applicationContext)) {
            permissionRequired.visibility = LinearLayout.GONE
        }

        val buildVersion = findViewById<TextView>(R.id.main_build_version)
        buildVersion.text = getString(R.string.main_version_tag, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        mainServiceIntent = Intent(this, MainService::class.java)
        startForegroundService(mainServiceIntent)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsMQTT(view: View) {
        val intent = Intent(this, PopupActivitySettingsMQTT::class.java)
        startActivityForResult(intent, REQUEST_CODE_SETTINGS_MQTT);
    }

    @Suppress("UNUSED_PARAMETER")
    fun openSettingsOSD(view: View) {
        val intent = Intent(this, PopupActivitySettingsOSD::class.java)
        startActivityForResult(intent, REQUEST_CODE_SETTINGS_VOLUME);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SETTINGS_MQTT ||
            requestCode == REQUEST_CODE_SETTINGS_VOLUME) {
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
