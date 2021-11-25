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

    @GET("user/cart")
    suspend fun getCart():Response<Cart>

    @Headers("Content-Type: application/json")
    @POST("cafeteria/cart")
    suspend fun postCart(@Body body:CartBody):Response<CartBody>

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cart")
    suspend fun updateCart(@Body body:CartBody):Response<CartBody>

    @DELETE("cafeteria/cart/{id}")
    suspend fun deleteCart(@Path("id")id:Int)

    @Headers("Content-Type: application/json")
    @POST("cafeteria")
    suspend fun postOrder():Response<OrderBody>

    @GET("user/orders")
    suspend fun getOrder(@Query("status") status:String):Response<Order>

    @GET("user/orders")
    suspend fun getSingleOrder(@Query("cartId") id: Int):Response<Order>

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cancel-order")
    suspend fun cancelOrder(@Body favoriteBody: FavoriteBody ):Response<CancelOrder>

    @POST("survey/avgteaboy")
    suspend fun getStar():Response<Star>

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cart")
    suspend fun updateStatus(@Body body:CartBody):Response<Like>

}