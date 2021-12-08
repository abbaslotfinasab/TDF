package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.OrderCountModel
import com.utechia.domain.repository.OrderCountRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderCountRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

):OrderCountRepo {
    override suspend fun getOrderCount(): MutableList<OrderCountModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getOrderCount()

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun setStatus(status: Boolean) = service.updateStatus(status)
}