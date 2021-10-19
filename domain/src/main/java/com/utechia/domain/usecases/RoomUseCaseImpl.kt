package com.utechia.domain.usecases


import com.utechia.domain.model.RoomModel
import com.utechia.domain.repository.RoomRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomUseCaseImpl @Inject constructor(private val roomRepo: RoomRepo):
    RoomUseCase<RoomModel> {

    override suspend fun execute(): MutableList<RoomModel> {
        return roomRepo.getRoom()
    }


}
