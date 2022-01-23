package com.utechia.data.repo


import com.utechia.data.api.Service
import com.utechia.data.entity.EventData
import com.utechia.data.entity.EventDetails
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.EventModel
import com.utechia.domain.repository.EventRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ):EventRepo {

    override suspend fun getEvent(status:String): MutableList<EventModel> {

    if (networkHelper.isNetworkConnected()) {

            val result = service.getAllEvent(1,500,status)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data!!.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")


    }


    override suspend fun apply(id: Int): MutableList<EventModel> {


   if (networkHelper.isNetworkConnected()) {

       val result = service.getEvaluate()

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
