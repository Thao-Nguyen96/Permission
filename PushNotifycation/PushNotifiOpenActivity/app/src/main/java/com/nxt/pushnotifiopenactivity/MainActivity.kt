package com.nxt.pushnotifiopenactivity

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

    private fun sendPushNotification() {
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bitmap = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.ic_launcher_background,
            null) as VectorDrawable).toBitmap()

        // Create an Intent for the activity you want to start
        val resultIntent = Intent(this, DetailActivity::class.java)
        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            //requestcode = id(notify)
            getPendingIntent(99, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID1)
            .setContentTitle("push notify cation channel 1")
            .setContentText("message push notify cation channel 1")
            .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
            .setLargeIcon(bitmap)
            .setSound(uri)
            .setContentIntent(resultPendingIntent)
            .setColor(Color.RED)
                //xoa notify khi đã click
            .setAutoCancel(true)
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(99, notificationCompat)
    }
}