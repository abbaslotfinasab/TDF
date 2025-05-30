package com.utechia.data.repo.login

import com.utechia.data.api.Service
import com.utechia.data.entity.notification.NotificationToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.login.VerifyModel
import com.utechia.domain.repository.ticket.VerifyRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyRepoImpl @Inject constructor(
    private val service: Service,
    private val sessionManager: SessionManager,
    private val networkHelper: NetworkHelper

): VerifyRepo {

    @Throws(IOException::class)
    override suspend fun verifyLogin(code: String): VerifyModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.verifyLogin(code)

            return when(result.isSuccessful && result.body() !=null){

                true ->{
                    result.body()?.data?.let {
                        sessionManager.saveAuthToken(it)
                    }
                    val fcmToken = sessionManager.fetchFireBaeToken()?:""

                    if(fcmToken.isNotEmpty())

                    service.notification(NotificationToken(fcmToken))

                    result.body()?.data!!.toDomain()

                }

                else ->
                    throw IOException("Server is not responding")
            }
        }
        else
            throw IOException("No Internet Connection")
    }
}