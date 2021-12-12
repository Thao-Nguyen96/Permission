package com.nxt.testnotification

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build

class MyApplication : Application() {

    companion object {
        const val CHANNEL = "channel"
    }

    @SuppressLint("WrongConstant")
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val soundUri =
                Uri.parse("android.resource://" + packageName + "/" + R.raw.sound)

            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel =
                NotificationChannel(CHANNEL, "channel 1", NotificationManager.IMPORTANCE_MAX)
            channel.description = "this is channel 1"
            channel.setSound(soundUri, attributes)

            val manager = getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}