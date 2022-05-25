package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.AnswerReservationModel
import com.utechia.domain.model.reservation.ReservationModel
import com.utechia.domain.repository.reservation.ReservationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationUseCaseImpl @Inject constructor(private val reservationRepo: ReservationRepo):
    ReservationUseCase<ReservationModel> {

    override suspend fun create(answerReservationModel: AnswerReservationModel): MutableList<ReservationModel> {
        return reservationRepo.crete(answerReservationModel)
    }

    override suspend fun add(id:Int) {
        reservationRepo.addGuess(id)
    }

    override suspend fun remove(id:Int) {
        reservationRepo.removeGuess(id)
    }

    override suspend fun deleteAll() {
        reservationRepo.deleteAll()
    }

    override suspend fun cancel(meetId: Int): MutableList<ReservationModel> {
        return reservationRepo.cancel(meetId)
    }
}