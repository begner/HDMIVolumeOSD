package nl.rogro82.pipup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.WindowManager
import android.widget.*
import com.begner.hdmivolumeosd.R

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

        setPadding(20,20,20,20)

        val title = findViewById<TextView>(R.id.vosd_title)
        title.text = props.curVolume.toString() + "/" + props.maxVolume.toString()

        val bar = findViewById<SeekBar>(R.id.vosd_bar)
        bar.max = props.maxVolume
        bar.progress = props.curVolume
    }

    open fun destroy() {}

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