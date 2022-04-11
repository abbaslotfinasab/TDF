package com.utechia.data.repo.order.teaboyorder

import com.utechia.data.api.Service
import com.utechia.data.entity.favorite.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.domain.repository.order.TeaBoyOrderDetailsRepo
import java.io.IOException
import javax.inject.Inject

class TeaBoyOrderDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): TeaBoyOrderDetailsRepo {

    override suspend fun singleOrderTeaBoy(id: Int): MutableList<TeaBoyOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSingleOrderTeaBoy(id)

            return when (result.isSuccessful && result.body() !=null) {

                true -> {
                    result.body()?.data!!.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else
            throw IOException("No Internet Connection")

    }

    override suspend fun acceptOrder(id: Int): MutableList<TeaBoyOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.acceptOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<TeaBoyOrderDataModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun rejectOrder(id: Int): MutableList<TeaBoyOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.rejectOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<TeaBoyOrderDataModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun deliveredOrder(id: Int):MutableList<TeaBoyOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.deliverOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<TeaBoyOrderDataModel>().toMutableList()

                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }
}