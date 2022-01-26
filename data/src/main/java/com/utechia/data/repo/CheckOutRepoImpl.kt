package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.OrderBodyModel
import com.utechia.domain.repository.CheckOutRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckOutRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper):CheckOutRepo {

    override suspend fun checkout(): OrderBodyModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.postOrder()

            return when (result.isSuccessful && result.body() !=null) {

                true -> {
                    result.body()!!.toDomain()
                }
                else -> {
                    throw IOException("Server is Not Responding")
                }
            }

        } else throw IOException("No Internet Connection")
    }
}