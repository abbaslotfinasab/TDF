package com.utechia.data.repo.health

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.health.TopStepsModel
import com.utechia.domain.repository.health.TopStepsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStepsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): TopStepsRepo {
    override suspend fun getTopUser(start:String,end:String): MutableList<TopStepsModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTopSteps(start,end)

            return when (result.isSuccessful && result.body() != null) {

                true -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun getChart(start:String , end:String): MutableList<TopStepsModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getChart(start,end)

            return when (result.isSuccessful && result.body() != null) {

                true -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }
}
