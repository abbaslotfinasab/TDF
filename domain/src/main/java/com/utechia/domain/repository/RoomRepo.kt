package com.utechia.domain.repository

import com.utechia.domain.moodel.RoomModel

interface RoomRepo {

    suspend fun getRoom(day_id: Int,month_id:Int):MutableList<RoomModel>

}