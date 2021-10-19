package com.utechia.domain.usecases

import com.utechia.domain.model.RoomModel

interface RoomUseCase<R> {

    suspend fun execute():MutableList<RoomModel>

}