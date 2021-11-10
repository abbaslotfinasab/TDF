package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.LoginModel
import com.utechia.domain.repository.LoginRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper

):LoginRepo {

    @Throws(IOException::class)

    override suspend fun getLogin(): LoginModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getLoginUrl()

            when {

                result.isSuccessful && result.body()?.data != null ->
                    return result.body()!!.data!!.toDomain()

                result.body()?.data == null ->
                    throw IOException("Login failed please try later")


                else ->
                    throw IOException("Server is Not Responding")

            }
        }
        else
            throw IOException("No Internet Connection")
    }
}