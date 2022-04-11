package com.utechia.domain.usecases.reservation


import com.utechia.domain.model.reservation.RoomModel
import com.utechia.domain.repository.reservation.RoomRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomUseCaseImpl @Inject constructor(private val roomRepo: RoomRepo):
    RoomUseCase<RoomModel> {

    override suspend fun execute(): MutableList<RoomModel> {
        return roomRepo.getRoom()
    }


}
