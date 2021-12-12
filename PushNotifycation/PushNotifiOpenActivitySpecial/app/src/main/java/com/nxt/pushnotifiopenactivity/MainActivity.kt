package com.nxt.pushnotifiopenactivity

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.nxt.pushnotifiopenactivity.MyApplication.Companion.CHANNEL_ID1
import com.nxt.pushnotifiopenactivity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Main Activity"

        binding.btnSendNotify.setOnClickListener {
            sendPushNotification()
        }

        binding.btnGoToListProduct.setOnClickListener {
            val intent = Intent(this, ListProductActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendPushNotification() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bitmap = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.ic_launcher_background,
            null) as VectorDrawable).toBitmap()

        val notifyIntent = Intent(this, DetailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 99, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID1)
            .setContentTitle("push notify cation channel 1")
            .setContentText("message push notify cation channel 1")
            .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
            .setLargeIcon(bitmap)
            .setSound(uri)
            .setContentIntent(notifyPendingIntent)
            .setColor(Color.RED)
            //xoa notify khi đã click
            .setAutoCancel(true)
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(99, notificationCompat)
    }
}