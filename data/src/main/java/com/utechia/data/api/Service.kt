package com.utechia.data.api

import com.utechia.data.entity.*
import org.json.JSONArray
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
    suspend fun like(@Body body:FavoriteBody)

    @DELETE("user/favorite/{id}")
    suspend fun dislike(@Path("id")id:Int)

    @GET("user/cart")
    suspend fun getCart():Response<Cart>

    @Headers("Content-Type: application/json")
    @POST("cafeteria/cart")
    suspend fun postCart(@Body body:CartBody)

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cart")
    suspend fun updateCart(@Body body:CartBody)

    @DELETE("cafeteria/cart/{id}")
    suspend fun deleteCart(@Path("id")id:Int):Response<Void>

    @Headers("Content-Type: application/json")
    @POST("cafeteria")
    suspend fun postOrder():Response<OrderBody>

    @GET("user/orders")
    suspend fun getOrder(@Query("status") status:String):Response<UserOrder>

    @GET("user/orders")
    suspend fun getSingleOrder(@Query("cartId") id: Int):Response<UserOrder>

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cancel-order")
    suspend fun cancelOrder(@Body favoriteBody: FavoriteBody ):Response<CancelOrder>

    @PATCH("teaboy/status")
    suspend fun updateStatus(@Query("status") status: Boolean):Response<Void>

    @GET("teaboy/order-count")
    suspend fun getOrderCount():Response<OrderCount>

    @GET("teaboy/orders")
    suspend fun getTeaBoyOrder(@Query("status") status:String):Response<TeaBoyOrder>

    @GET("teaboy/orders")
    suspend fun getSingleOrderTeaBoy(@Query("cartId") id: Int):Response<TeaBoyOrder>

    @Headers("Content-Type: application/json")
    @PATCH("teaboy/reject-order")
    suspend fun rejectOrder(@Body favoriteBody: FavoriteBody ):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("teaboy/accept-order")
    suspend fun acceptOrder(@Body favoriteBody: FavoriteBody ):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("teaboy/delivered-order")
    suspend fun deliverOrder(@Body favoriteBody: FavoriteBody ):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("user/firebase")
    suspend fun notification(@Body token: NotificationToken)

    @GET("accessmanagement/permissions")
    suspend fun getPermission(@Query("status") status:String):Response<Permission>

    @GET("accessmanagement/permissions")
    suspend fun getSinglePermission(@Query("id") id:Int):Response<Permission>

    @Headers("Content-Type: application/json")
    @POST("accessmanagement/permissions")
    suspend fun postPermission(@Body permissionPostBody: PermissionPostBody):Response<Permission>

    @Headers("Content-Type: application/json")
    @PATCH("accessmanagement/permissions")
    suspend fun updatePermission(@Body permissionUpdateBody: PermissionUpdateBody):Response<Void>

    @GET("accessmanagement/permissions/permissiontypes")
    suspend fun getPermissionType():Response<PermissionType>

    @GET("survey/listactive")
    suspend fun getSurveyList():Response<Survey>

    @GET("survey/getarray/{id}")
    suspend fun getSurvey(@Path("id")id: Int):Response<Survey>

    @POST("survey/evaluted")
    suspend fun getEvaluate():Response<Survey>

    @Headers("Content-Type: application/json")
    @POST("survey/answer")
    suspend fun postAnswer(@Body answer: JSONArray):Response<Void>

    @Headers("Content-Type: application/json")
    @POST("survey/rateorder")
    suspend fun rateOrder(@Body rateOrderBody: OrderRateBody):Response<OrderRate>

}