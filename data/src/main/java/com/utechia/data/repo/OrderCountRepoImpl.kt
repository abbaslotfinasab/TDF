package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.OrderCountModel
import com.utechia.domain.repository.OrderCountRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderCountRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,

):OrderCountRepo {
    override suspend fun getOrderCount(): MutableList<OrderCountModel> {

        if (networkHelper.isNetworkConnected()) {

            var result = service.getOrderCount()

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.getOrderCount()
            }
            return when (result.code()) {

                200 -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")


    }

    override suspend fun setStatus(status: Boolean) = service.updateStatus(status)
}