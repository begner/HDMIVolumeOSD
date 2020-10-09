package com.begner.hdmivolumeosd

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class ActivityGlobal : AppCompatActivity() {

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}