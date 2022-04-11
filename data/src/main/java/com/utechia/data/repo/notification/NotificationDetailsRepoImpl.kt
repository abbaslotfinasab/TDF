package com.utechia.data.repo.notification

import com.utechia.data.api.Service
import com.utechia.data.entity.notification.DeleteNotificationBody
import com.utechia.data.entity.notification.NotificationBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.repository.notification.NotificationDetailsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): NotificationDetailsRepo {
    override suspend fun delete(id: Int,deleteAll:Boolean?): MutableList<NotificationModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.deleteNotification(DeleteNotificationBody(id,deleteAll?:false))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<NotificationModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun read(id: Int,readAll:Boolean?): MutableList<NotificationModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.readNotification(NotificationBody(id,readAll?:false))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<NotificationModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }
}