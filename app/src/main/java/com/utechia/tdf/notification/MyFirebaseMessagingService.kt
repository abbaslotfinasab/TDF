package com.utechia.tdf.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
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

    private fun getRemoteView(title:String, message:String):RemoteViews{
        val remoteView = RemoteViews (channel_Name,R.layout.item_push_notification)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.subTitle,message)
        return remoteView

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if(remoteMessage.notification!=null)
        generateNotification(remoteMessage.notification?.title!!,remoteMessage.notification?.body!!)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    private fun generateNotification(title: String, message: String) {

        val  bundle = bundleOf("cartId" to 148)

        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(R.id.teaBoyNotificationFragment)
            .setArguments(bundle)
            .createTaskStackBuilder()
            .getPendingIntent(0,PendingIntent.FLAG_ONE_SHOT)


        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channel_Id)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            val notificationChannel = NotificationChannel(channel_Id, channel_Name,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }
}