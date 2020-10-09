package com.begner.hdmivolumeosd

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReceiverWakeUp : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        with(context) {
            val serviceIntent = Intent(this, MainService::class.java)
            startForegroundService(serviceIntent)
        }
    }
}
