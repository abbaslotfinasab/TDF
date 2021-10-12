package com.utechia.domain.usecases

import com.utechia.domain.moodel.ReservationModel
import com.utechia.domain.repository.ReservationRepo
import javax.inject.Inject

class ReservationUseCaseImpl @Inject constructor(private val reservationRepo: ReservationRepo):
    ReservationUseCase<ReservationModel> {

    override suspend fun reserve(reservationModel:ReservationModel) {
        return reservationRepo.reserve(reservationModel)

    }

    override suspend fun getAll(): MutableList<ReservationModel> {
        return  reservationRepo.getAll()
    }

    override suspend fun get(id: Int): ReservationModel {
      return reservationRepo.get(id)
    }
}