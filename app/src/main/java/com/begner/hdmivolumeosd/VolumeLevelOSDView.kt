package nl.rogro82.pipup

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.begner.hdmivolumeosd.PropertyOSDPositions
import com.begner.hdmivolumeosd.R
import com.begner.hdmivolumeosd.SettingsMQTT
import com.begner.hdmivolumeosd.SettingsVolume
import java.lang.Math.floor
import java.lang.Math.round


// TODO: convert dimensions from px to dp

@SuppressLint("ViewConstructor")
sealed class VolumeLevelOSDView(context: Context, val props: VolumeLevelOSDProps) : LinearLayout(context) {


    @RequiresApi(Build.VERSION_CODES.R)
    open fun create() {

        val settingsMQTT = SettingsMQTT(context)
        var settingsVolume = SettingsVolume(context)

        if (PropertyOSDPositions().getPositionByKey(settingsVolume.getPosition()).isHorizontal) {
            inflate(context, R.layout.volume_osd_horizontal,this)
        } else {
            inflate(context, R.layout.volume_osd_vertical,this)
        }


        val title = findViewById<TextView>(R.id.vosd_volume)
        title.text = mapVolume(props.curVolume).toString() // + "/" + mapVolume(props.maxVolume).toString()

        val bar = findViewById<ProgressBar>(R.id.vosd_bar)
        bar.max = mapVolume(props.maxVolume)
        bar.progress = mapVolume(props.curVolume)


        val temp = findViewById<TextView>(R.id.vosd_temp)
        if (settingsMQTT.getMQTTActive()) {
            val tempRounded = round(props.curTemp * 10).toFloat() / 10f
            temp.text = tempRounded.toString() + "°C"
        } else {
            temp.visibility = View.GONE
        }

        val speakerIcon = findViewById<ImageView>(R.id.vosd_icon_speaker)
        if (mapVolume(props.curVolume) < 1) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_0)
        } else if (mapVolume(props.curVolume) < 8) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_1)
        } else if (mapVolume(props.curVolume) < 15) {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_2)
        } else {
            speakerIcon.setImageResource(R.drawable.ic_icon_speaker_3)
        }

    }


    open fun destroy() {}

    fun mapVolume(volume: Int): Int {
        var versatz = floor(volume.toDouble() / 6.toDouble()).toInt()
        return volume - versatz
    }

    private class Default(context: Context, props: VolumeLevelOSDProps) : VolumeLevelOSDView(context, props) {
        init {
            create()
        }
    }

    companion object {
        const val LOG_TAG = "VolumeLevelOSD"

        fun build(context: Context, props: VolumeLevelOSDProps): VolumeLevelOSDView
        {
            return Default(context, props)
        }
    }
}