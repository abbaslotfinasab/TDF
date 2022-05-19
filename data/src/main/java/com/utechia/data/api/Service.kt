package com.utechia.data.api

import com.utechia.data.entity.notification.NotificationCount
import com.utechia.data.entity.cart.Cart
import com.utechia.data.entity.cart.CartBody
import com.utechia.data.entity.cart.LocationBody
import com.utechia.data.entity.cart.Office
import com.utechia.data.entity.event.Event
import com.utechia.data.entity.event.EventDetails
import com.utechia.data.entity.event.RateEvent
import com.utechia.data.entity.favorite.Favorite
import com.utechia.data.entity.favorite.FavoriteBody
import com.utechia.data.entity.home.Average
import com.utechia.data.entity.health.Steps
import com.utechia.data.entity.health.TopSteps
import com.utechia.data.entity.home.TeaBoyActive
import com.utechia.data.entity.login.Login
import com.utechia.data.entity.login.Verify
import com.utechia.data.entity.main.RefreshToken
import com.utechia.data.entity.main.Token
import com.utechia.data.entity.notification.DeleteNotificationBody
import com.utechia.data.entity.notification.Notification
import com.utechia.data.entity.notification.NotificationBody
import com.utechia.data.entity.notification.NotificationToken
import com.utechia.data.entity.order.*
import com.utechia.data.entity.permission.Permission
import com.utechia.data.entity.permission.PermissionPostBody
import com.utechia.data.entity.permission.PermissionType
import com.utechia.data.entity.permission.PermissionUpdateBody
import com.utechia.data.entity.profile.Profile
import com.utechia.data.entity.refreshment.Refreshment
import com.utechia.data.entity.survey.SingleSurvey
import com.utechia.data.entity.survey.Survey
import com.utechia.data.entity.ticket.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Service {

    @GET("auth/login")
    suspend fun getLoginUrl(): Response<Login>

    @Headers("Content-Type: application/json")
    @POST("auth/refresh-token")
    suspend fun refresh(@Body body: RefreshToken):Response<Token>

    @POST("auth/verify-login")
    @FormUrlEncoded
    suspend fun verifyLogin(@Field("code")code:String): Response<Verify>

    @GET("cafeteria")
    suspend fun getRefreshment(): Response<Refreshment>

    @GET("cafeteria")
    suspend fun search(@Query("search")Search:String, @Query("type")type:String): Response<Refreshment>

    @GET("user/favorite")
    suspend fun getFavorite():Response<Favorite>

    @GET("user/favorite")
    suspend fun exist(@Query("food")title:String):Response<Favorite>

    @Headers("Content-Type: application/json")
    @POST("user/favorite")
    suspend fun like(@Body body: FavoriteBody)

    @DELETE("user/favorite/{id}")
    suspend fun dislike(@Path("id")id:Int)

    @GET("user/cart")
    suspend fun getCart():Response<Cart>

    @Headers("Content-Type: application/json")
    @POST("cafeteria/cart")
    suspend fun postCart(@Body body: CartBody)

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cart")
    suspend fun updateCart(@Body body: CartBody)

    @DELETE("cafeteria/cart/{id}")
    suspend fun deleteCart(@Path("id")id:Int):Response<Void>

    @DELETE("cafeteria/cart/{id}")
    suspend fun deleteRefreshment(@Path("id")id:Int)

    @Headers("Content-Type: application/json")
    @POST("cafeteria")
    suspend fun postOrder(@Body locationBody: LocationBody):Response<OrderBody>

    @GET("user/orders")
    suspend fun getOrder(@Query("status") status:String,@Query("page") page: Int,@Query("page_size") page_size: Int):Response<UserOrder>

    @GET("user/orders")
    suspend fun getSingleOrder(@Query("cartId") id: Int):Response<UserOrder>

    @Headers("Content-Type: application/json")
    @PATCH("cafeteria/cancel-order")
    suspend fun cancelOrder(@Body favoriteBody: FavoriteBody):Response<Void>

    @PATCH("teaboy/status")
    suspend fun updateStatus(@Query("status") status: Boolean,@Query("floorId") floorId: Int):Response<Void>

    @GET("teaboy/order-count")
    suspend fun getOrderCount():Response<OrderCount>

    @GET("teaboy/orders")
    suspend fun getTeaBoyOrder(@Query("status") status:String,@Query("page") page: Int,@Query("page_size") page_size: Int):Response<TeaBoyOrder>

    @GET("teaboy/orders")
    suspend fun getSingleOrderTeaBoy(@Query("cartId") id: Int):Response<TeaBoyOrder>

    @Headers("Content-Type: application/json")
    @PATCH("teaboy/reject-order")
    suspend fun rejectOrder(@Body favoriteBody: FavoriteBody):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("teaboy/accept-order")
    suspend fun acceptOrder(@Body favoriteBody: FavoriteBody):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("teaboy/delivered-order")
    suspend fun deliverOrder(@Body favoriteBody: FavoriteBody):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("user/firebase")
    suspend fun notification(@Body token: NotificationToken)

    @GET("accessmanagement/permissions")
    suspend fun getPermission(@Query("status") status:String,@Query("page") page: Int,@Query("page_size") page_size: Int):Response<Permission>

    @GET("accessmanagement/permissions")
    suspend fun getSinglePermission(@Query("id") id:Int):Response<Permission>

    @Headers("Content-Type: application/json")
    @POST("accessmanagement/permissions")
    suspend fun postPermission(@Body permissionPostBody: PermissionPostBody):Response<Void>

    @Headers("Content-Type: application/json")
    @PATCH("accessmanagement/permissions")
    suspend fun updatePermission(@Body permissionUpdateBody: PermissionUpdateBody):Response<Void>

    @GET("accessmanagement/permissions/permissiontypes")
    suspend fun getPermissionType():Response<PermissionType>

    @GET("survey/listactive")
    suspend fun getSurveyList(@Query("page") page: Int,@Query("page_size") page_size: Int):Response<Survey>

    @GET("survey/getarray/{id}")
    suspend fun getSurvey(@Path("id")id: Int):Response<SingleSurvey>

    @POST("survey/evaluted")
    suspend fun getEvaluate(@Query("page") page: Int,@Query("page_size") page_size: Int):Response<Survey>

    @Headers("Content-Type: application/json")
    @POST("survey/answer")
    suspend fun postAnswer(@Body answer: String):Response<Void>

    @Headers("Content-Type: application/json")
    @POST("survey/rateorder")
    suspend fun rateOrder(@Body rateOrderBody: OrderRateBody):Response<OrderRate>

    @GET("ticket/getalluserticket")
    suspend fun getTicketList(@Query("status") status: String,@Query("page") page: Int,@Query("page_size") page_size: Int):Response<Ticket>

    @GET("ticket/{id}")
    suspend fun getTicket(@Path("id") id: Int):Response<TicketDetails>

    @Headers("Content-Type: application/json")
    @POST("ticket")
    suspend fun postTicket(@Body ticketBody: TicketBody):Response<Void>

    @POST("ticket/close/{id}")
    suspend fun closeTicket(@Path("id")id: Int):Response<Void>


    @POST("ticket/baseneeds")
    suspend fun getNeeds():Response<BaseNeeds>

    @Headers("Content-Type: application/json")
    @POST("ticket/replymessagefromuser")
    suspend fun reply(@Body replyBody: ReplyBody):Response<Void>

    @Multipart
    @POST("ticket/upload")
    suspend fun uploadFile(@Part file:MultipartBody.Part):Response<Upload>

    @Headers("Content-Type: application/json")
    @POST("survey/rateTicket")
    suspend fun rateTicket(@Body rateTicketBody: RateTicketBody):Response<Void>

    @GET("notification")
    suspend fun getNotification(@Query("page") page: Int,@Query("page_size") page_size: Int):Response<Notification>

    @PATCH("notification/read")
    suspend fun readNotification(@Body notificationBody: NotificationBody):Response<Void>

    @HTTP(method = "DELETE", path = "notification",hasBody = true)
    suspend fun deleteNotification(@Body deleteNotificationBody: DeleteNotificationBody):Response<Void>

    @GET("user/items/count")
    suspend fun countNotification():Response<NotificationCount>

    @GET("event")
    suspend fun getAllEvent(@Query("page") page: Int,@Query("page_size") page_size: Int,@Query("statustype") status: String):Response<Event>

    @GET("event/{id}")
    suspend fun getEvent(@Path("id")id:Int):Response<EventDetails>

    @POST("event/apply/{eventid}")
    suspend fun applyEvent(@Path("eventid")eventid:Int):Response<Void>

    @POST("event/cancelapply/{contributerid}")
    suspend fun cancelEvent(@Path("contributerid")contributerid:Int):Response<Void>

    @POST("survey/rateevent")
    suspend fun rateEvent(@Body rateEvent: RateEvent):Response<Void>

    @POST("survey/avgteaboy")
    suspend fun avg():Response<Average>

    @GET("health/steps/top")
    suspend fun getTopSteps(@Query("fromdate")fromdate:String ,@Query("todate")todate:String):Response<TopSteps>

    @POST("health/steps")
    @Headers("Content-Type: application/json")
    suspend fun sendSteps(@Body steps: Steps):Response<Void>

    @GET("health/steps/chart")
    suspend fun getChart(@Query("fromdate")fromdate:String ,@Query("todate")todate:String):Response<TopSteps>

    @GET("user/info")
    suspend fun getProfile():Response<Profile>

    @GET("location")
    suspend fun getOffice():Response<Office>

    @GET("teaboy/status")
    suspend fun getTeaBoyActive():Response<TeaBoyActive>

    @GET("reservation/room")
    suspend fun getReservationRoom():Response<TeaBoyActive>


}