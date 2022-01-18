package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.TicketModel
import com.utechia.domain.repository.TicketDetailsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketDetailsRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): TicketDetailsRepo {

    override suspend fun getSingleTicket(id: Int): TicketModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTicket(id)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.toDomain()!!
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}
