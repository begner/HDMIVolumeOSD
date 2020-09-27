package nl.rogro82.pipup

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.begner.hdmivolumeosd.MQTTSettings
import com.begner.hdmivolumeosd.R
import java.lang.Math.floor
import java.lang.Math.round


// TODO: convert dimensions from px to dp

@SuppressLint("ViewConstructor")
sealed class VolumeLevelOSDView(context: Context, val props: VolumeLevelOSDProps) : LinearLayout(context) {


    open fun create() {
        inflate(context, R.layout.volumeosd,this)

        layoutParams = LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        ).apply {
            orientation = VERTICAL
            minimumWidth = 240
        }


        val settings = MQTTSettings(context)

        setPadding(20,20,20,20)

        val title = findViewById<TextView>(R.id.vosd_title)
        title.text = mapVolume(props.curVolume).toString() // + "/" + mapVolume(props.maxVolume).toString()

        val bar = findViewById<SeekBar>(R.id.vosd_bar)
        bar.max = mapVolume(props.maxVolume)
        bar.progress = mapVolume(props.curVolume)



        val tempContainer = findViewById<LinearLayout>(R.id.vsod_temp_container)
        if (settings.getMQTTActive()) {
            val tempRounded = round(props.curTemp * 10).toFloat() / 10f
            val temp = findViewById<TextView>(R.id.vosd_temp)
            temp.text = tempRounded.toString() + "°C"
        } else {
            (tempContainer.getParent() as ViewGroup).removeView(tempContainer)
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