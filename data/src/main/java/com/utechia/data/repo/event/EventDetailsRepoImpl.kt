package com.utechia.data.repo.event

import com.utechia.domain.model.event.EventModel
import com.utechia.domain.repository.event.EventDetailRepo
import java.io.IOException
import com.utechia.data.api.Service
import com.utechia.data.entity.event.RateEvent
import com.utechia.data.utile.NetworkHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): EventDetailRepo {


    override suspend fun getEventByID(id: Int): MutableList<EventModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getEvent(id)

            return when (result.isSuccessful && result.body() !=null) {

                true -> {
                    listOf(result.body()?.data?.toDomain()!!).toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun apply(id: Int): MutableList<EventModel> {


        if (networkHelper.isNetworkConnected()) {

            val result = service.applyEvent(id)

            return when (result.isSuccessful) {

                true -> {
                    emptyList<EventModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun cancel(id: Int): MutableList<EventModel> {


        if (networkHelper.isNetworkConnected()) {

            val result = service.cancelEvent(id)

            return when (result.isSuccessful) {

                true -> {
                    emptyList<EventModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun rate(id: Int, rate: Int): MutableList<EventModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.rateEvent(RateEvent(rate,id))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<EventModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}

