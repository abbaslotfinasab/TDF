package com.utechia.domain.usecases

import com.utechia.domain.moodel.RoomModel

interface RoomUseCase<R> {

    suspend fun execute(day_id:Int,month_id:Int):MutableList<RoomModel>

}