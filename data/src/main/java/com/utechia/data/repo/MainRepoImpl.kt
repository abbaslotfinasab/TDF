package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.NotificationCountModel
import com.utechia.domain.repository.MainRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
):MainRepo {

    override suspend fun getCountNotification(): NotificationCountModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.countNotification()

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.toDomain()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}