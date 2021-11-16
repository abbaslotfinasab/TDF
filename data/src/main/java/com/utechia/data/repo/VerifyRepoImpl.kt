package com.utechia.data.repo

import com.utechia.data.api.Service
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

            when {

                result.isSuccessful && result.body()?.data != null -> {

                    sessionManager.saveAuthToken(
                        result.body()!!.data!!.token!!,
                        result.body()!!.data!!.userHomeId!!,
                        result.body()!!.data!!.isTeaBoy!!,
                    )
                    return result.body()!!.data?.toDomain()!!
                }

                result.body()?.error == null -> {
                    throw IOException("Login failed please try later")
                }

                else -> {
                    throw IOException("Server is Not Responding")
                }
            }
        }
        else
            throw IOException("No Internet Connection")
    }
}