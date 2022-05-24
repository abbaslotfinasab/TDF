package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.AnswerReservationModel

interface ReservationUseCase<R> {
    suspend fun create(answerReservationModel: AnswerReservationModel):MutableList<R>
    suspend fun add(id:Int)
    suspend fun remove(id:Int)
    suspend fun deleteAll()
}