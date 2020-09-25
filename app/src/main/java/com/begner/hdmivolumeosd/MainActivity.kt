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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.begner.hdmivolumeosd.R


class MainActivity : Activity() {

    lateinit var settingsMQTTServer: TextView
    lateinit var settingsMQTTTopic: TextView
    lateinit var settingsMQTTUser: TextView
    lateinit var settingsMQTTPassword: TextView
    lateinit var settings: Settings
    lateinit var mainServiceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*
        if (!Settings.canDrawOverlays(applicationContext)) {

            // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
            val requestPermissionLauncher =
                registerForActivityResult(RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        // Permission is granted. Continue the action or workflow in your
                        // app.
                    } else {
                        // Explain to the user that the feature is unavailable because the
                        // features requires a permission that the user has denied. At the
                        // same time, respect the user's decision. Don't link to system
                        // settings in an effort to convince the user to change their
                        // decision.
                    }
                }
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Rights needed")
            builder.setMessage("Foo bar")

            val requestCode: Int = 123;

            builder.setPositiveButton("YES") { dialog, which ->
                try {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + applicationContext.packageName)
                    )
                    startActivityForResult(intent, requestCode);
                } catch (e: ActivityNotFoundException) {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    startActivityForResult(intent, requestCode);
                }
            }

            builder.setNegativeButton("No"){dialog,which ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        else {

 */
            setContentView(R.layout.activity_main)
            mainServiceIntent = Intent(this, MainService::class.java)

            startForegroundService(mainServiceIntent)
      //  }


        settingsMQTTServer = findViewById(R.id.settings_mqtt_server);
        settingsMQTTTopic = findViewById(R.id.settings_mqtt_topic);
        settingsMQTTUser = findViewById(R.id.settings_mqtt_username);
        settingsMQTTPassword = findViewById(R.id.settings_mqtt_password);

        settings = Settings(applicationContext)
        GetSettings(null)
    }

    fun restartService() {
        stopService(mainServiceIntent)
        startForegroundService(mainServiceIntent)
    }

    fun SaveSettings(view: View?) {
        settings.SaveSettings(settingsMQTTServer.getText().toString(),
                                settingsMQTTTopic.getText().toString(),
                                settingsMQTTUser.getText().toString(),
                                settingsMQTTPassword.getText().toString())
        restartService()
    }

    fun ClearSettings(view: View?) {
        settingsMQTTServer.setText("")
        settingsMQTTTopic.setText("")
        settingsMQTTUser.setText("")
        settingsMQTTPassword.setText("")
    }

    fun GetSettings(view: View?) {
        settingsMQTTServer.setText(settings.getMQTTServer())
        settingsMQTTTopic.setText(settings.getMQTTTopic())
        settingsMQTTUser.setText(settings.getMQTTUser())
        settingsMQTTPassword.setText(settings.getMQTTPassword())
    }


}
