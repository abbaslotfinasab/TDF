package com.utechia.data.repo.reservation

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.reservation.SingleMeetingModel
import com.utechia.domain.repository.reservation.SingleMeetingRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SingleMeetingRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,

): SingleMeetingRepo {

    override suspend fun getSingle(id:Int): SingleMeetingModel {

        if (networkHelper.isNetworkConnected()) {
            Log.d("meetId",id.toString())
            val result = service.getSingleMeeting(id)
            Log.d("resultMeet",result.body().toString())

            if (result.isSuccessful){
                return result.body()?.data?.toDomain()!!
            }else
                throw IOException("Server is not responding")

        } else throw IOException("No Internet Connection")
    }
}

