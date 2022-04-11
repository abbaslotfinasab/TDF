package com.utechia.data.repo.order.teaboyorder

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.order.OrderCountModel
import com.utechia.domain.repository.order.OrderCountRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderCountRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager


): OrderCountRepo {
    override suspend fun getOrderCount(): MutableList<OrderCountModel> {

        if (networkHelper.isNetworkConnected()) {

            val avg = service.avg()

            if(avg.isSuccessful && avg.body()?.data!=null ){
                avg.body()?.data?.AVG?.let { sessionManager.saveAvg(it) }
            }

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

    override suspend fun setStatus(status: Boolean):MutableList<OrderCountModel>{

        if (networkHelper.isNetworkConnected()) {

            val result = service.updateStatus(status)

            return when (result.isSuccessful) {

                true -> {
                    emptyList<OrderCountModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}