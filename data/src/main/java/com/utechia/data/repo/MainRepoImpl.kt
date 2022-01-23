package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.NotificationToken
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.NotificationCountModel
import com.utechia.domain.repository.MainRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager

    ):MainRepo {

    override suspend fun getCountNotification(): NotificationCountModel {

        if (networkHelper.isNetworkConnected()) {

            var result = service.countNotification()
            val homeId = sessionManager.fetchHomeId()?:""

                if (result.code()== 401 || result.code()==403 ){

                val token = service.refresh( RefreshToken(homeId)).body()?.data.toString()
                    sessionManager.updateAuthToken(token)
                    result = service.countNotification()
                }

            return when (result.code()) {

                200 ->{

                    val fcmToken = sessionManager.fetchFireBaeToken()?:""

                    if(fcmToken.isNotEmpty())

                        service.notification(NotificationToken(fcmToken))

                    result.body()?.data!!.toDomain()

                }

                401 -> {
                    throw IOException("Unauthorized")

                }

                403 -> {
                    throw IOException("Unauthorized")

                }

                else -> {
                    throw IOException("Server is Not Responding")
                }
            }

        } else throw IOException("No Internet Connection")
    }
}