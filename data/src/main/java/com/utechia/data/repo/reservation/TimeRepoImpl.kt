package com.utechia.data.repo.reservation

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.reservation.TimeModel
import com.utechia.domain.repository.reservation.TimeRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TimeRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,

): TimeRepo {

    override suspend fun getTimeMeeting(date: String, id: Int): MutableList<TimeModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getMeetingTime(id,date)

            return when (result.isSuccessful && result.body()?.data !=null){

                true -> {
                    result.body()?.data?.map {
                        it.toDomain()
                    }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")

            }

        } else throw IOException("No Internet Connection")

    }

}
