package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.CartModel
import com.utechia.domain.model.FavoriteModel
import com.utechia.domain.model.ItemModel
import com.utechia.domain.repository.CartRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,


    ):CartRepo {
    override suspend fun getCart(): MutableList<CartModel> {
        if (networkHelper.isNetworkConnected()) {

            var result = service.getCart()

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.getCart()
            }

            return when (result.code()) {

                200 -> {
                    result.body()?.data?.cart?.map{ it.toDomain()}!!.toMutableList()
                }

                404 ->
                    return emptyList<CartModel>().toMutableList()

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun postCart(id: Int, quantity: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCart(id: Int, quantity: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCart(id: Int) {
        TODO("Not yet implemented")
    }
}