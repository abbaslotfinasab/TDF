package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.entity.NotificationToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.VerifyModel
import com.utechia.domain.repository.VerifyRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyRepoImpl @Inject constructor(
    private val service: Service,
    private val sessionManager: SessionManager,
    private val networkHelper: NetworkHelper

):VerifyRepo {

    @Throws(IOException::class)
    override suspend fun verifyLogin(code: String): VerifyModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.verifyLogin(code)

            return when(result.isSuccessful){

                true ->{

                    sessionManager.saveAuthToken(result.body()!!.data!!)

                    service.notification(NotificationToken(sessionManager.fetchFireBaeToken()!!))

                    Log.d("fcm",sessionManager.fetchFireBaeToken().toString())

                    result.body()!!.data!!.toDomain()

                }
                else ->
                    throw IOException("Server is not responding")
            }
        }
        else
            throw IOException("No Internet Connection")
    }
}