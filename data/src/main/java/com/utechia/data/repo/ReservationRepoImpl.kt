package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.dao.ReservationDao
import com.utechia.data.mapper.ReservationMapper
import com.utechia.data.utile.NetworkHandler
import com.utechia.domain.moodel.ReservationModel
import com.utechia.domain.repository.ReservationRepo
import java.io.IOException
import javax.inject.Inject

class ReservationRepoImpl @Inject constructor(

    private val reservationMapper:dagger.Lazy<ReservationMapper>,
    private val reservationDao: ReservationDao,
    private val service: Service,
    private val networkHandler: NetworkHandler

    ): ReservationRepo {

    @Throws(IOException::class)
    override suspend fun reserve(reservationModel: ReservationModel) {

        if(networkHandler.isNetworkConnected()) {

            reservationDao.saveReservation(reservationMapper.get().toMapper(reservationModel))
        }

        else
            throw IOException("No Internet Connection")


    }

    override suspend fun getAll(): MutableList<ReservationModel> {

        if(networkHandler.isNetworkConnected()) {

            return reservationDao.getAll().map {

                reservationMapper.get().inMapper(it)

            }.toMutableList()

        }
        else
            throw IOException("No Internet Connection")

    }

    override suspend fun get(id: Int): ReservationModel {

        if(networkHandler.isNetworkConnected()) {

            return reservationMapper.get().inMapper(reservationDao.get(id))

        }
        throw IOException("No Internet Connection")

    }

}
