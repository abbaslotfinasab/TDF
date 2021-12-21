package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.entity.OrderRateBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.repository.OrderRateRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRateRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
):OrderRateRepo {
    override suspend fun rate(order: Int, rate: Int): Boolean {

        Log.d("working" , "ok")

        if (networkHelper.isNetworkConnected()) {

            val result = service.rateOrder(OrderRateBody(order,rate))

            return when (result.isSuccessful) {

                true -> result.body()?.data?.success!!

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }
}