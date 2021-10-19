package com.utechia.domain.repository

import com.utechia.domain.model.RoomModel

interface RoomRepo {

    suspend fun getRoom():MutableList<RoomModel>

}