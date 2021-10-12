package com.utechia.domain.repository

import com.utechia.domain.moodel.ReservationModel

interface ReservationRepo {

    suspend fun reserve(reservationModel:ReservationModel)
    suspend fun getAll():MutableList<ReservationModel>
    suspend fun get(id:Int):ReservationModel

}