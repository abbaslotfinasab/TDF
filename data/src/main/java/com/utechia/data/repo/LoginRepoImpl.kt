package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.LoginModel
import com.utechia.domain.repository.LoginRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepoImpl @Inject constructor(

    private val service: Service,
    private val sessionManager: SessionManager


):LoginRepo {

    override suspend fun getLogin(): LoginModel {

        if (service.getLoginUrl().isSuccessful && service.getLoginUrl().body()?.data != null){

            return service.getLoginUrl().body()?.data!!.toDomain()

        }

        else
            throw IOException(service.getLoginUrl().body()?.error?.message!![0])

    }

    override suspend fun verifyLogin(code: String) {

        if (service.verifyLogin(code).isSuccessful) {

            Log.d("username", "Worked")

            service.verifyLogin(code).body()
                .let { sessionManager.saveAuthToken(it?.data?.token!!,
                    it.data.userHomeId!!
                ) }
        }
        else {
            throw IOException(service.verifyLogin(code).body()?.error?.message!![0])
        }
    }
}