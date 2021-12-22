package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.entity.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.repository.OrderRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
):OrderRepo {
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

    override suspend fun cancelOrder(id: Int) {

        if (networkHelper.isNetworkConnected()) {

            val result = service.cancelOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {}

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

    override suspend fun acceptOrder(id: Int) {

        if (networkHelper.isNetworkConnected()) {

            val result = service.acceptOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {}

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun rejectOrder(id: Int) {

        if (networkHelper.isNetworkConnected()) {

            val result = service.rejectOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {}

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun deliveredOrder(id: Int) {

        if (networkHelper.isNetworkConnected()) {

            val result = service.deliverOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {}

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun getTeaBoyOrder(status:String): MutableList<OrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTeaBoyOrder(status)

            return when (result.isSuccessful) {

                true -> {

                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun singleOrderTeaBoy(id: Int): MutableList<OrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSingleOrderTeaBoy(id)

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