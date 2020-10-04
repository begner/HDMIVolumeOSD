package com.begner.hdmivolumeosd

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.leanback.widget.Visibility
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout


class PopupActivitySettingsTemperature : PopupActivity(), TabLayout.OnTabSelectedListener {

    val Active: com.google.android.material.switchmaterial.SwitchMaterial = findViewById(R.id.active)
    val Position: Spinner = findViewById(R.id.position)
    val MinTemp: TextView = findViewById(R.id.min_temp)
    val MaxTemp: TextView = findViewById(R.id.max_temp)
    val Server: TextView = findViewById(R.id.server)
    val Topic: TextView = findViewById(R.id.topic)
    val User: TextView = findViewById(R.id.username)
    val Password: TextView = findViewById(R.id.password)
    val NavigationTab: TabLayout = findViewById(R.id.navigationTab)
    val TabList: List<TabData> = listOf(
        TabData("main", "Main", findViewById(R.id.main_settings)),
        TabData("mqtt", "Mqtt", findViewById(R.id.mqtt_settings)),
    )

    lateinit var settingsTemperature: SettingsTemperature

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_temperature)

        settingsTemperature = SettingsTemperature(applicationContext)

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            OSDPositionsTemperature().getLabelArray()
        )
        Position.setAdapter(arrayAdapter);

        NavigationTab.addOnTabSelectedListener(this)
        TabList.forEach {
            NavigationTab.addTab(NavigationTab.newTab().setText(it.label))
        }

        fill()
    }

    private fun fill() {
        Active.setChecked(settingsTemperature.getMQTTActive())
        Position.setSelection(OSDPositionsTemperature().getIndexByKey(settingsTemperature.getPosition()))
        MinTemp.setText(settingsTemperature.getMinTemp().toString())
        MaxTemp.setText(settingsTemperature.getMaxTemp().toString())
        Server.setText(settingsTemperature.getMQTTServer())
        Topic.setText(settingsTemperature.getMQTTTopic())
        User.setText(settingsTemperature.getMQTTUser())
        Password.setText(settingsTemperature.getMQTTPassword())
    }

    override fun save() {
        settingsTemperature.SaveSettings(
            Active.isChecked(),
            OSDPositionsTemperature().getByIndex(Position.selectedItemPosition).key,
            MinTemp.getText().toString().toInt(),
            MaxTemp.getText().toString().toInt(),
            Server.getText().toString(),
            Topic.getText().toString(),
            User.getText().toString(),
            Password.getText().toString()
        )
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val tabIndex = NavigationTab.selectedTabPosition
        TabList.forEachIndexed { index, tabData ->
            if (index == tabIndex) {
                tabData.contentContainer.visibility = ScrollView.VISIBLE
            }
            else {
                tabData.contentContainer.visibility = ScrollView.GONE
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    data class TabData (
        var key: String = "",
        var label: String = "",
        var contentContainer: LinearLayout
    )
}

