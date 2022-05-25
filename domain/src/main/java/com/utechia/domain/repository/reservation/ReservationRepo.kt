package com.utechia.domain.repository.reservation

import com.utechia.domain.model.reservation.AnswerReservationModel
import com.utechia.domain.model.reservation.ReservationModel


interface ReservationRepo {

    suspend fun crete(answerReservationModel: AnswerReservationModel):MutableList<ReservationModel>
    suspend fun cancel(meetId:Int):MutableList<ReservationModel>
    suspend fun addGuess(id:Int)
    suspend fun removeGuess(id:Int)
    suspend fun deleteAll()

}