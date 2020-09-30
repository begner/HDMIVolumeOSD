package nl.rogro82.pipup

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
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


        val title = findViewById<TextView>(R.id.vosd_title)
        title.text = mapVolume(props.curVolume).toString() // + "/" + mapVolume(props.maxVolume).toString()

        val bar = findViewById<SeekBar>(R.id.vosd_bar)
        bar.max = mapVolume(props.maxVolume)
        bar.progress = mapVolume(props.curVolume)
/*
        if (!PropertyOSDPositions().getPositionByKey(settingsVolume.getPosition()).isHorizontal) {
            val barContainer = findViewById<FrameLayout>(R.id.vosd_bar_container)

            var lp = bar.layoutParams
            lp.height = 40
            lp.width = 400
            bar.setLayoutParams(lp)
        }
*/
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

    open fun afterDraw() {
        var settingsVolume = SettingsVolume(context)

        val mainContainer = findViewById<LinearLayout>(R.id.vosd_main_container)
        mainContainer.gravity = PropertyOSDPositions().getPositionByKey(settingsVolume.getPosition()).gravity

        val metrics = context.getResources().getDisplayMetrics()

        val osdContainer = findViewById<ConstraintLayout>(R.id.vosd_osd_container)
        val osdContainerLayoutParams = osdContainer.layoutParams


        if (PropertyOSDPositions().getPositionByKey(settingsVolume.getPosition()).isHorizontal) {
            osdContainerLayoutParams.width = round(metrics.widthPixels.toFloat() / 100f * settingsVolume.getSize().toFloat())
        } else {
            osdContainerLayoutParams.height = round(metrics.heightPixels.toFloat() / 100f * settingsVolume.getSize().toFloat())
        }
        osdContainer.layoutParams = osdContainerLayoutParams
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