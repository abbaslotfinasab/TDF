package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.RoomModel

interface RoomUseCase<R> {

    suspend fun execute():MutableList<RoomModel>

}