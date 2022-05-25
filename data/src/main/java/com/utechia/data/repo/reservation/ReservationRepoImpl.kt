package com.utechia.data.repo.reservation

import com.utechia.data.api.Service
import com.utechia.data.dao.ProfileDao
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.reservation.AnswerReservationModel
import com.utechia.domain.model.reservation.ReservationModel
import com.utechia.domain.repository.reservation.ReservationRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val profileDao: ProfileDao

    ): ReservationRepo {

    override suspend fun crete(answerReservationModel: AnswerReservationModel): MutableList<ReservationModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.createMeeting(answerReservationModel)

            return when (result.isSuccessful){

                true -> {
                    emptyList<ReservationModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun cancel(meetId: Int): MutableList<ReservationModel> {
        if (networkHelper.isNetworkConnected()) {

            val result = service.cancelMeet(meetId)

            return when (result.isSuccessful){

                true -> {
                    emptyList<ReservationModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun addGuess(id: Int) {
        profileDao.add(id,true)
    }

    override suspend fun removeGuess(id: Int) {
        profileDao.add(id,false)
    }

    override suspend fun deleteAll() {
        profileDao.deleteAll()
    }
}