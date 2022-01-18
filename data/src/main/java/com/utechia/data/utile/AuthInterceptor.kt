package com.utechia.data.utile

import android.content.Context
import com.utechia.data.api.Service
import com.utechia.data.entity.RefreshToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(@ApplicationContext context: Context,private val service: dagger.Lazy<Service>) : Authenticator {

    private val sessionManager = SessionManager(context)

    override fun authenticate(route: Route?, response: Response): Request {

        return runBlocking{

            updateToken(response.code).let {
                response.request.newBuilder().header("Authorization", "Bearer $it")
                    .build()
            }
        }
    }

    private suspend fun updateToken(code:Int):String {

        return if (code==402 || code==403){
            val result = service.get().refresh( RefreshToken(
                sessionManager.fetchHomeId()
                    .toString()
            ))
            sessionManager.updateAuthToken(result.body()?.data!!)

            result.body()?.data!!
        } else
            sessionManager.fetchAuthToken()!!
    }
}
