package com.utechia.data.repo.order.userorder


import com.utechia.data.api.Service
import com.utechia.data.entity.favorite.FavoriteBody
import com.utechia.data.entity.order.OrderRateBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.order.UserOrderDataModel
import com.utechia.domain.repository.profile.UserOrderDetailsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): UserOrderDetailsRepo {

    override suspend fun cancelOrder(id: Int): MutableList<UserOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.cancelOrder(FavoriteBody(id))

            return when (result.isSuccessful) {

                true -> emptyList<UserOrderDataModel>().toMutableList()

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun singleOrder(id: Int): MutableList<UserOrderDataModel>{

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSingleOrder(id)

            return when (result.isSuccessful && result.body() !=null) {

                true -> {
                    result.body()?.data?.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else
            throw IOException("No Internet Connection")
    }

    override suspend fun rate(order: Int, rate: Int): MutableList<UserOrderDataModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.rateOrder(OrderRateBody(order, rate))

            return when (result.isSuccessful) {

                true -> emptyList<UserOrderDataModel>().toMutableList()

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}