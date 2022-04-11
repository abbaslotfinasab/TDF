package com.utechia.domain.repository.event

import com.utechia.domain.model.event.EventModel

interface EventDetailRepo {

    suspend fun getEventByID(id: Int):MutableList<EventModel>
    suspend fun apply(id:Int):MutableList<EventModel>
    suspend fun cancel(id:Int):MutableList<EventModel>
    suspend fun rate(id:Int,rate:Int):MutableList<EventModel>

}