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

            return when(result.isSuccessful){

                true ->{

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