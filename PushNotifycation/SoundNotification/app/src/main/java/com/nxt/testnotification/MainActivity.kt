package com.nxt.testnotification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.nxt.testnotification.MyApplication.Companion.CHANNEL
import com.nxt.testnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Main Activity"

        val button = binding.btn

        button.setOnClickListener {
            sendNotification()
        }

    }

    private fun sendNotification() {

        val soundUri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.sound)

        val bitmap = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.yeah,
            null) as BitmapDrawable).toBitmap()
        val bitmap2 = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.gau4, null) as BitmapDrawable).toBitmap()

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


        val notification = NotificationCompat.Builder(this, CHANNEL)
            .setContentTitle("YEAH1 MUSIC")
            .setContentText("MV CHẲNG THẾ NÀO CÒN YÊU THƯƠNG MÃI MỘT NGƯỜI")
            .setSmallIcon(R.drawable.video)
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap2))
            .setColor(Color.RED)
            .setSound(soundUri)
            .addAction(R.mipmap.ic_launcher, "PHÁT", resultPendingIntent)
            .addAction(R.mipmap.ic_launcher,
                "TẮT", null)
            .addAction(R.mipmap.ic_launcher, "XEM SAU", null)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(1, notification)
    }
}