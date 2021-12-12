package com.nxt.mediastyle

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.nxt.mediastyle.MyApplication.Companion.CHANNEL_ID
import com.nxt.mediastyle.databinding.ActivityMainBinding

//https://developer.android.com/training/notify-user/expanded
//them vào thư viện media

import androidx.media.app.NotificationCompat as MediaNotificationCompat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            senNotificationMedia()
        }
    }

    private fun senNotificationMedia() {

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.gau8)

        val mediaSession = MediaSessionCompat(this, "tag")

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_music_note_24)
            .setSubText("Thanh Huyen")
            .setContentTitle("Title of song")
            .setContentText("Single of song")
            .setLargeIcon(bitmap)
            .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", null) // #0
            .addAction(R.drawable.ic_baseline_pause_24, "Pause", null) // #1
            .addAction(R.drawable.ic_baseline_skip_next_24, "Next", null) // #2
            .setStyle(MediaNotificationCompat.MediaStyle()
                //hiển thì vị trí button action muốn nhìn thấy ở dạng thu gọn notify
                .setShowActionsInCompactView(1 /* #1: pause button \*/)
                .setMediaSession(mediaSession.sessionToken))
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(2, notification)
    }
}