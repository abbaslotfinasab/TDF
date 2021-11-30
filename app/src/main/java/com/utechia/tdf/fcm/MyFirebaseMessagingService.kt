package com.utechia.tdf.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.utechia.tdf.R
import com.utechia.tdf.activity.MainActivity


const val channel_Id = "notification_channel"
const val channel_Name = "com.utechia.tdf"

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        generateNotification(remoteMessage.data["cartId"])


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("token",token)



    }
    private fun generateNotification(id: String?) {

        val  bundle = bundleOf("cartId" to id )

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(R.id.teaBoyNotificationFragment)
            .setArguments(bundle)
            .createTaskStackBuilder()
            .getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE)


        val builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channel_Id)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            val notificationChannel = NotificationChannel(channel_Id, channel_Name,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }
}