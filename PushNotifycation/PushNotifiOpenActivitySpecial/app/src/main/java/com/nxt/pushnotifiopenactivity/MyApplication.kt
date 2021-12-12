package com.nxt.pushnotifiopenactivity

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build

//https://developer.android.com/training/notify-user/navigation

class MyApplication: Application() {

    companion object{
        const val CHANNEL_ID1 = "CHANNEL_ID_1"
    }

    override fun onCreate() {
        super.onCreate()

        createNotification()
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)

            val channel = NotificationChannel(CHANNEL_ID1,
                name,
                NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = description
            channel.setSound(uri, attributes)

            if (Build.VERSION.SDK_INT < 26) {
                return
            }

            val manager =
                getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}