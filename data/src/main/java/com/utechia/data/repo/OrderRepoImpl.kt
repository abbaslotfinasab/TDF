package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.repository.OrderRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,

):OrderRepo {
    override suspend fun getOrder(status:String): MutableList<OrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            var result = service.getOrder(status)

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.getOrder(status)
            }
            Log.d("code", result.body()?.data!![0].cart.status.toString())
            return when (result.code()) {

                200 -> {
                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun cancelOrder(id:Int) {

        if (networkHelper.isNetworkConnected()) {

            var result = service.cancelOrder(id)

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.cancelOrder(id)
            }

            return when (result.code()) {

                200 ->{}

                404 ->
                    throw IOException("Not found")

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}