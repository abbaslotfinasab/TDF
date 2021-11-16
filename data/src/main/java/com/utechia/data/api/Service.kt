package com.utechia.data.api

import com.utechia.data.entity.Login
import com.utechia.data.entity.Refreshment
import com.utechia.data.entity.Verify
import retrofit2.Response
import retrofit2.http.*

interface Service {

    @GET("auth/login")
    suspend fun getLoginUrl(): Response<Login>

    @POST("auth/verify-login")
    @FormUrlEncoded
    suspend fun verifyLogin(@Field("code")code:String): Response<Verify>

    @GET("cafeteria")
    suspend fun getRefreshment(@Query("type")type:String): Response<MutableList<Refreshment>>

    @GET("cafeteria")
    suspend fun search(@Query("search")Search:String): Response<MutableList<Refreshment>>

    @POST("cafeteria")
    suspend fun getCart(@Query("foodId")foodId:Int): Response<MutableList<Refreshment>>

}