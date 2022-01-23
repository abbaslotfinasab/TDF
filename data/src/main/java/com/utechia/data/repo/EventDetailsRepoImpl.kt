package com.utechia.data.repo

import com.utechia.domain.model.EventModel
import com.utechia.domain.repository.EventDetailRepo
import java.io.IOException
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ):EventDetailRepo {


    override suspend fun getEventByID(id: Int): EventModel {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getEvent(id)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.toDomain()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}

