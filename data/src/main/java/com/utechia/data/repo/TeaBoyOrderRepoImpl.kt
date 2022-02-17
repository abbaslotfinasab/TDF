package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.TeaBoyOrderDataModel
import com.utechia.domain.repository.TeaBoyOrderRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyOrderRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    ):TeaBoyOrderRepo {

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

    override suspend fun getTeaBoyOrder(status:String): MutableList<TeaBoyOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTeaBoyOrder(status, PagingEnum.Number.page, PagingEnum.Size.page)

            return when (result.isSuccessful && result.body() !=null) {

                true -> {

                    result.body()?.data!!.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

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
}