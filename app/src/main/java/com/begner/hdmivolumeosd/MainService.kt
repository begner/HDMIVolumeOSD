package com.begner.hdmivolumeosd

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.text.SimpleDateFormat
import java.util.*

class MainService : Service() {
    private var osd: OSD = OSD()

    private lateinit var mqttClient: MqttClient
    private var temperatures: MutableMap<String, Float> = mutableMapOf<String, Float>()
    private var lastMqttValue: Long = System.currentTimeMillis()


    override fun onCreate() {
        super.onCreate()

        initNotificationChannel("service_channel", "Service channel", "Service channel")

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, ActivityMain::class.java), 0
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
        startMqtt()
        osd.initialize(this, applicationContext)
        osd.createOverlay()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        osd.destroy()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun initNotificationChannel(id: String, name: String, description: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            id, name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = description
        notificationManager.createNotificationChannel(channel)
    }

    fun getLastMqttValue(): Long {
        return lastMqttValue
    }

    fun getAverageTemp(): Float {
        var avarageTemp : Float = 0f
        if (temperatures.isNotEmpty()) {
            for (item in temperatures) {
                avarageTemp += item.value
            }
            avarageTemp /= temperatures.size
        }
        return avarageTemp
    }

    fun startMqtt() {
        temperatures = mutableMapOf<String, Float>()

        mqttClient = MqttClient(applicationContext)
        mqttClient.setCallback(object : MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) {
                Log.w("Mqtt", "connectionLost")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.w("Mqtt", "deliveryComplete")
            }

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.w("Mqtt", "connectComplete")
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                temperatures.put(topic, mqttMessage.toString().toFloat())
                lastMqttValue = System.currentTimeMillis()
            }
        })

    }

    companion object {
        const val ONGOING_NOTIFICATION_ID = 123
    }
}
