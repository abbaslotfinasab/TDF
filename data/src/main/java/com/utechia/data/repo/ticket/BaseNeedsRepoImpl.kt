package com.utechia.data.repo.ticket

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.ticket.BaseNeedsModel
import com.utechia.domain.repository.ticket.BaseNeedsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseNeedsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper
): BaseNeedsRepo {

    override suspend fun getNeeds(): BaseNeedsModel {
        if (networkHelper.isNetworkConnected()) {

            val result = service.getNeeds()

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.toDomain()
                }
                else -> {
                    throw IOException("Server is Not Responding")
                }
            }

        } else throw IOException("No Internet Connection")
    }

}