package com.utechia.data.api

import com.utechia.data.entity.*
import retrofit2.Response
import retrofit2.http.*

interface Service {

    @GET("auth/login")
    suspend fun getLoginUrl(): Response<Login>

    @Headers("Content-Type: application/json")
    @POST("auth/refresh-token")
    suspend fun refresh(@Body body:RefreshToken):Response<Token>

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

    @GET("user/favorite")
    suspend fun exist(@Query("food")title:String):Response<Favorite>

    @Headers("Content-Type: application/json")
    @POST("user/favorite")
    suspend fun like(@Body body:FavoriteBody):Response<Like>

    @DELETE("user/favorite/{id}")
    suspend fun dislike(@Path("id")id:Int)

}