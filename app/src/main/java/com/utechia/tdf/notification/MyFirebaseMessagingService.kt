package com.utechia.tdf.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.utechia.tdf.R
import com.utechia.tdf.main.MainActivity


const val channel_Id = "notification_channel"
const val channel_Name = "com.utechia.tdf"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var prefs: SharedPreferences


    private fun getRemoteView(title:String, message:String):RemoteViews{
        val remoteView = RemoteViews (channel_Name,R.layout.item_push_notification)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.subTitle,message)
        remoteView.setImageViewResource(R.id.image,R.drawable.ic_notification)
        return remoteView

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if(remoteMessage.notification!=null && remoteMessage.data.isNotEmpty())
        generateNotification(remoteMessage.data["type"]!!,remoteMessage.data["referenceId"]!!.toLong(),remoteMessage.notification?.title!!,remoteMessage.notification?.body!!)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        prefs = getSharedPreferences("tdf", MODE_PRIVATE)

        with(prefs.edit()) {
            putString("fcm",token)
        }.apply()

    }

    private fun generateNotification(type:String, referenceId:Long, title: String, message: String) {

        var builder : NotificationCompat.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationCompat.Builder(applicationContext, channel_Id)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentTitle(message)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000,1000,1000,1000))
                .setOnlyAlertOnce(true)
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(navigate(type,referenceId))
                .setChannelId(channel_Id)
        } else {
            NotificationCompat.Builder(applicationContext, channel_Id)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentTitle(message)
                .setVibrate(longArrayOf(1000,1000,1000,1000))
                .setOnlyAlertOnce(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(navigate(type,referenceId))
                .setChannelId(channel_Id)

        }

        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){

            val notificationChannel = NotificationChannel(channel_Id, channel_Name,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0,builder.build())
    }

    private fun navigate(type: String, referenceId: Long): PendingIntent? {

        when(type){

            "Teaboy"->{

                val bundle = bundleOf("cartId" to referenceId)

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.teaBoyNotificationFragment)
                    .setArguments(bundle)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            "Ticket"->{

                val bundle = bundleOf("fid" to referenceId)

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.ticketDetailsFragment)
                    .setArguments(bundle)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            "Cafeteria"->{

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.orderFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            "Permission"->{

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.permissionFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            else -> {
                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.nav_graph)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)
            }
        }
    }
}