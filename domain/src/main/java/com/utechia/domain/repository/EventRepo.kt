package com.utechia.domain.repository

import com.utechia.domain.model.EventModel

interface EventRepo {

    suspend fun getEvent():MutableList<EventModel>
    suspend fun apply(id:Int):MutableList<EventModel>

}