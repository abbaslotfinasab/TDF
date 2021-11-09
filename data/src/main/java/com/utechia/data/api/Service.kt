package com.utechia.data.api

import com.utechia.data.entity.Login
import com.utechia.data.entity.Verify
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("auth/login")
    suspend fun getLoginUrl(): Response<Login>

    @POST("auth/verify-login")
    @FormUrlEncoded
    suspend fun verifyLogin(@Field("code")code:String): Response<Verify>

}