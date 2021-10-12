package com.utechia.domain.usecases

import com.utechia.domain.moodel.ReservationModel

interface ReservationUseCase<R> {

    suspend fun reserve(reservationModel:ReservationModel)
    suspend fun getAll(): MutableList<R>
    suspend fun get(id:Int):ReservationModel



}