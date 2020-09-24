package com.begner.hdmivolumeosd

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.provider.Settings.canDrawOverlays
import androidx.core.app.NotificationCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import nl.rogro82.pipup.VolumeLevelOSD


class MainService : Service() {
    private lateinit var volumeLevelOSD: VolumeLevelOSD

    override fun onCreate() {
        super.onCreate()

        initNotificationChannel("service_channel", "Service channel", "Service channel")

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), 0
        )

        val mBuilder = NotificationCompat.Builder(this, "service_channel")
            .setContentTitle("HDMI Volume OSD")
            .setContentText("Service running")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setAutoCancel(false)
            .setOngoing(true)


        startForeground(Companion.ONGOING_NOTIFICATION_ID, mBuilder.build())
        volumeLevelOSD = VolumeLevelOSD(this, applicationContext)
    }


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        volumeLevelOSD.destroy()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun initNotificationChannel(id: String, name: String, description: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(id, name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = description
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val ONGOING_NOTIFICATION_ID = 123
    }
}
