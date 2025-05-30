package com.utechia.data.repo.main

import com.utechia.data.api.Service
import com.utechia.data.entity.notification.NotificationToken
import com.utechia.data.entity.main.RefreshToken
import com.utechia.data.entity.health.Steps
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.main.NotificationCountModel
import com.utechia.domain.repository.main.MainRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager

    ): MainRepo {

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
                    result.body()?.data!!.toDomain()
                }

                401 -> {
                    throw IOException("Unauthorized")

                }

                else -> {
                    throw IOException("Server is Not Responding")
                }
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun sendToken() {

        val fcmToken = sessionManager.fetchFireBaeToken()?:""

        if(fcmToken.isNotEmpty())
            service.notification(NotificationToken(fcmToken))
    }

    override suspend fun sendSteps(steps: Int, calory: Int, start: String, end: String) {
        service.sendSteps(Steps(steps,calory,end))
    }
}