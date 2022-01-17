package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.NotificationModel
import com.utechia.domain.repository.NotificationRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    ):NotificationRepo {
    override suspend fun getAll(): MutableList<NotificationModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getNotification(1,500)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.list?.map{it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun delete(id: Int): MutableList<NotificationModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.deleteNotification(id)

            return when (result.isSuccessful) {

                true -> {
                    emptyList<NotificationModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun read(id: Int): MutableList<NotificationModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.readNotification(id)

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