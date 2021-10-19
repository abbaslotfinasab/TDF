package com.utechia.domain.usecases

import com.utechia.domain.model.ReservationModel
import com.utechia.domain.repository.ReservationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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