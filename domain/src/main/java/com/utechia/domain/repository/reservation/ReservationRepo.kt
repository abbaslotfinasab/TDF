package com.utechia.domain.repository.reservation

import com.utechia.domain.model.reservation.ReservationModel

interface ReservationRepo {

    suspend fun reserve(reservationModel: ReservationModel)
    suspend fun getAll():MutableList<ReservationModel>
    suspend fun get(id:Int): ReservationModel

}