package com.luisenricke.breakingmyservices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

// reference: https://androidwave.com/foreground-service-android-example-in-kotlin/
class ForegroundNotification : Service() {

    companion object {
        private const val FOREGROUND_ID = 1010

        private const val CHANNEL_ID = "ForegroundNotificationChannelID"
        private const val CHANNEL_NAME = "Foreground Notification Channel"

        private const val NOTIFICATION_TITLE = "Foreground Service Kotlin Example"
        private const val NOTIFICATION_ICON = android.R.drawable.ic_dialog_alert

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundNotification::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundNotification::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onCreate() = super.onCreate()
        .also { Toast.makeText(this, "Created foreground notification", Toast.LENGTH_SHORT).show() }

    override fun onDestroy() = super.onCreate()
        .also { Toast.makeText(this, "Stopped foreground notification", Toast.LENGTH_SHORT).show() }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // return super.onStartCommand(intent, flags, startId)
        Log.i("ForegroundNotification", "Sending notification")

        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(input)
            .setSmallIcon(NOTIFICATION_ICON)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(FOREGROUND_ID, notification)

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null // doesn't bind any view in this services
}
