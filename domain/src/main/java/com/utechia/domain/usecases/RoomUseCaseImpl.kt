package com.utechia.domain.usecases


import com.utechia.domain.moodel.RoomModel
import com.utechia.domain.repository.RoomRepo
import javax.inject.Inject

class RoomUseCaseImpl @Inject constructor(private val roomRepo: RoomRepo):
    RoomUseCase<RoomModel> {

    override suspend fun execute(day_id: Int,month_id:Int): MutableList<RoomModel> {
        return roomRepo.getRoom(day_id,month_id)
    }


}
