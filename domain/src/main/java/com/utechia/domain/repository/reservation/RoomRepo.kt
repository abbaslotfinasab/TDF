package com.utechia.domain.repository.reservation

import com.utechia.domain.model.reservation.RoomModel

interface RoomRepo {

    suspend fun getRoom(query:String?=null):MutableList<RoomModel>

}