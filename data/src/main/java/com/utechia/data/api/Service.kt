package com.utechia.data.api

import com.utechia.data.entity.Favorite
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
    suspend fun getRefreshment(@Query("type")type:String): Response<Refreshment>

    @GET("cafeteria")
    suspend fun search(@Query("search")Search:String, @Query("type")type:String): Response<Refreshment>

    @POST("cafeteria")
    @FormUrlEncoded
    suspend fun getCart(@Field("foodId")foodId:Int): Response<Refreshment>

    @GET("user/favorite")
    suspend fun getFavorite():Response<Favorite>

    @POST("user/favorite")
    @FormUrlEncoded
    suspend fun like(@Field("id") id:Int)

    @DELETE("user/favorite/{id}")
    suspend fun dislike(@Path("id")id:Int)

}