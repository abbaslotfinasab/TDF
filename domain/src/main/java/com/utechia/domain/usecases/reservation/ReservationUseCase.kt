package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.ReservationModel

interface ReservationUseCase<R> {

    suspend fun reserve(reservationModel: ReservationModel)
    suspend fun getAll(): MutableList<R>
    suspend fun get(id:Int): ReservationModel



}