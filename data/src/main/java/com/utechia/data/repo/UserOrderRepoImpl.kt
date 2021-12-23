package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.repository.UserOrderRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
):UserOrderRepo {
    override suspend fun getOrder(status: String): MutableList<OrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getOrder(status)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun cancelOrder(id: Int): MutableList<OrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.cancelOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<OrderDataModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun singleOrder(id: Int): MutableList<OrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSingleOrder(id)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else
            throw IOException("No Internet Connection")
    }
}