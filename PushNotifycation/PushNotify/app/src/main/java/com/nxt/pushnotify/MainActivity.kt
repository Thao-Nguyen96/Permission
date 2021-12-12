package com.nxt.pushnotify

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.nxt.pushnotify.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

//https://developer.android.com/training/notify-user/custom-notification

class MainActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID1 = "CHANNEL_ID_1"
        const val CHANNEL_ID2 = "CHANNEL_ID_2"
        const val TITLE_PUSH = "Ronaldo lập cú đúp ngày tái xuất"
        const val MESSAGE_PUSH =
            "Bí ẩn Ronaldo nói gì giúp MU thăng hoa, đàn em \"cắp sách\" học CR7"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSendNotification.setOnClickListener {
            sendNotifyCation()
        }
        binding.btnSendNotification2.setOnClickListener {
            sendNotifyCation2()
        }

        binding.btnCustomNotification.setOnClickListener {
            customNotification()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val soundUri =
                Uri.parse("android.resource://" + packageName + "/" + R.raw.sound)


            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            //config channel 1
            // id, name, importance: do uu tien ĐỂ HIỂN THỊ

            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)

            val channel = NotificationChannel(CHANNEL_ID1,
                name,
                NotificationManager.IMPORTANCE_MIN)
            channel.description = description
            channel.setSound(uri, attributes)

            //config channel 2

            val name2 = getString(R.string.channel_name_2)
            val description2 = getString(R.string.channel_description_2)

            val channel2 = NotificationChannel(CHANNEL_ID2,
                name2,
                NotificationManager.IMPORTANCE_HIGH)
            channel2.description = description2
            channel2.setSound(soundUri, attributes)

            if (Build.VERSION.SDK_INT < 26) {
                return
            }

            val manager =
                getSystemService(NotificationManager::class.java) as NotificationManager
            manager.createNotificationChannel(channel)
            manager.createNotificationChannel(channel2)
        }
    }

    private fun sendNotifyCation() {

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bitmap = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.ic_launcher_background,
            null) as VectorDrawable).toBitmap()

        val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID1)
            .setContentTitle("push notify cation channel 1")
            .setContentText("message push notify cation channel 1")
            .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
            .setLargeIcon(bitmap)
            .setSound(uri)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setColor(Color.RED)
            .setAutoCancel(true)
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        //neu muon nhan nhieu thong bao phai thay doi id
        managerCompat.notify(1, notificationCompat)
    }


    private fun sendNotifyCation2() {
        val soundUri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.sound)
        val bitmap = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.ronaldo,
            null) as BitmapDrawable).toBitmap()

        val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID2)
            .setContentTitle(TITLE_PUSH)
            .setContentText(MESSAGE_PUSH)
            .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
            .setLargeIcon(bitmap)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
            .setSound(soundUri)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setColor(Color.RED)
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        //neu muon nhan nhieu thong bao phai thay doi id
        managerCompat.notify(2, notificationCompat)
    }

    @SuppressLint("SimpleDateFormat")
    private fun customNotification() {

        //sử dụng bitmap khi tràn menory vì ảnh quá to

        val bitmap = (ResourcesCompat.getDrawable(this.resources,
            R.drawable.ronaldo,
            null) as BitmapDrawable).toBitmap()

        val soundUri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.sound)

//-------------------------

        val notificationLayout = RemoteViews(packageName, R.layout.layout_custom)
        notificationLayout.setTextViewText(R.id.tv_title, "Title custom notification")
        notificationLayout.setTextViewText(R.id.tv_message, "message custom notification")

        val sdf = SimpleDateFormat("HH:mm:ss")
        val strDate = sdf.format(Date())

        notificationLayout.setTextViewText(R.id.tv_time, strDate)
        //expanded: hieenr thị ảnh to--------------------------------------------------------------------------

        val notificationLayoutExpanded = RemoteViews(packageName, R.layout.layout_custom_expanded)

        notificationLayoutExpanded.setTextViewText(R.id.tv_title_expanded, "Title custom notification")
        notificationLayoutExpanded.setTextViewText(R.id.tv_message_expanded, "message custom notification")
        notificationLayoutExpanded.setImageViewBitmap(R.id.img_expanded, bitmap)

        //------------------------------------------------------------------------------

        val notificationCompat = NotificationCompat.Builder(this, CHANNEL_ID2)
            .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
            .setSound(soundUri)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            //.setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        val managerCompat = NotificationManagerCompat.from(this)
        //neu muon nhan nhieu thong bao phai thay doi id
        managerCompat.notify(2, notificationCompat)
    }
}