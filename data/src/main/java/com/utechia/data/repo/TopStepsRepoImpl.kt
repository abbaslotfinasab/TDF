package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.TopStepsModel
import com.utechia.domain.repository.TopStepsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopStepsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): TopStepsRepo {
    override suspend fun getTopUser(): MutableList<TopStepsModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTopSteps()

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
