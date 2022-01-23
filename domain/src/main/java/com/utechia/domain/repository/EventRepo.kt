package com.utechia.domain.repository

import com.utechia.domain.model.EventModel

interface EventRepo {

    suspend fun getEvent(status:String):MutableList<EventModel>
    suspend fun getEventByID(id: Int):MutableList<EventModel>
    suspend fun apply(id:Int):MutableList<EventModel>

}