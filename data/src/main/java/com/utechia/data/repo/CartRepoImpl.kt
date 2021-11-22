package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.CartBody
import com.utechia.data.entity.CartData
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.CartDataModel
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
    override suspend fun getCart(): MutableList<CartDataModel> {

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

                200 ->
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()

                404 ->
                     emptyList<CartDataModel>().toMutableList()
                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun postCart(id: Int, quantity: Int) {

        if (networkHelper.isNetworkConnected()) {

            var result = service.postCart(CartBody(id,quantity))

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.postCart(CartBody(id,quantity))
            }

            return when (result.code()) {

                200 -> {

                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun updateCart(id: Int, quantity: Int) {

        if (networkHelper.isNetworkConnected()) {

            var result = service.updateCart(CartBody(id,quantity))

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.updateCart(CartBody(id,quantity))
            }

            return when (result.code()) {

                200 -> {}

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun deleteCart(id: Int) = service.deleteCart(id)

}