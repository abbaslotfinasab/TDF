package com.utechia.tdf.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.utechia.domain.enum.*
import com.utechia.tdf.R
import com.utechia.tdf.main.MainActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var prefs: SharedPreferences? = null

    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView =
            RemoteViews(NotificationEnum.ChannelName.notification, R.layout.item_push_notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.subTitle, message)
        remoteView.setImageViewResource(R.id.image, R.drawable.ic_notification)
        return remoteView
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        NotificationListener.notificationListener.postValue(true)
        if (remoteMessage.notification != null && remoteMessage.data.isNullOrEmpty()) {
            remoteMessage.data[NotificationEnum.Type.notification]?.let { type ->
                remoteMessage.data[NotificationEnum.ReferenceId.notification]?.toLong()
                    .let {refID ->
                        remoteMessage.data[NotificationEnum.Access.notification]
                            ?.let {
                                if (it ==NotificationEnum.Admin.notification){
                                    remoteMessage.data[NotificationEnum.Url.notification]?.let {
                                        url ->
                                        generateNotification(
                                            type,
                                            refID,
                                            url,
                                            remoteMessage.notification?.title
                                                ?: getString(R.string.new_notification),
                                            remoteMessage.notification?.body ?: ""
                                        )
                                    }
                                }
                                else{
                                    generateNotification(
                                        type,
                                        refID,
                                        null,
                                        remoteMessage.notification?.title
                                            ?: getString(R.string.new_notification),
                                        remoteMessage.notification?.body ?: ""
                                    )
                                }
                            }
                    }
            }

            val intent = Intent("counter")
            intent.putExtra("action", "count")
            this.sendBroadcast(intent)

        } else if (remoteMessage.notification != null) {
            generateNotification(
                null,
                null,
                null,
                remoteMessage.notification?.title ?: getString(R.string.new_notification),
                remoteMessage.notification?.body ?: ""
            )

            val intent = Intent("counter")
            intent.putExtra("action", "count")
            this.sendBroadcast(intent)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        prefs = getSharedPreferences(MainEnum.Tdf.main, MODE_PRIVATE)

        with(prefs?.edit()) {
            this?.putString("fcm", token)
        }?.apply()

    }

    private fun generateNotification(
        type: String?,
        referenceId: Long?,
        url: String?,
        title: String,
        message: String
    ) {

        var builder: NotificationCompat.Builder =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                NotificationCompat.Builder(
                    applicationContext,
                    NotificationEnum.ChannelId.notification
                )
                    .setSmallIcon(R.drawable.ic_tdf_notification)
                    .setContentTitle(title)
                    .setContentTitle(message)
                    .setAutoCancel(true)
                    //.setVibrate(longArrayOf(1000,1000,1000,1000))
                    //.setOnlyAlertOnce(true)
                    .setPriority(NotificationManager.IMPORTANCE_MAX)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(navigate(type,referenceId,url))
                    .setChannelId(NotificationEnum.ChannelId.notification)
            } else {
                NotificationCompat.Builder(
                    applicationContext,
                    NotificationEnum.ChannelId.notification
                )

                    .setSmallIcon(R.drawable.ic_tdf_notification)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentTitle(message)
                    //.setVibrate(longArrayOf(1000,1000,1000,1000))
                    //.setOnlyAlertOnce(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(navigate(type,referenceId,url))
                    .setChannelId(NotificationEnum.ChannelId.notification)

            }

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                NotificationEnum.ChannelId.notification,
                NotificationEnum.ChannelName.notification, NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    private fun navigate(type: String?, referenceId: Long?,url: String?): PendingIntent? {

        when (type) {

            NotificationEnum.TeaBoy.notification -> {

                val bundle = bundleOf(OrderEnum.ID.order to referenceId?.toInt())

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setArguments(bundle)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.teaBoyNotificationFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Ticket.notification -> {

                val bundle = bundleOf(TicketEnum.Id.ticket to referenceId?.toInt())

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setArguments(bundle)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.ticketDetailsFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Cafeteria.notification -> {

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.orderFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Permission.notification -> {

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.permissionFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Survey.notification -> {

                val bundle = bundleOf(SurveyEnum.Id.survey to referenceId?.toInt())

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setArguments(bundle)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.createSurveyFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Event.notification -> {

                val bundle = bundleOf(EventsEnum.ID.event to referenceId?.toInt())

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setArguments(bundle)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.eventDetailsFragment)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Custom.notification -> {

                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setComponentName(MainActivity::class.java)
                    .setDestination(R.id.nav_graph)
                    .createTaskStackBuilder()
                    .getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT)

            }

            NotificationEnum.Admin.notification -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                return PendingIntent.getActivity(
                    this,
                    0,
                    browserIntent,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE
                else
                        PendingIntent.FLAG_ONE_SHOT

                )
            }

            else -> {
                return NavDeepLinkBuilder(applicationContext)
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.nav_graph)
                    .createPendingIntent()
            }
        }
    }
}