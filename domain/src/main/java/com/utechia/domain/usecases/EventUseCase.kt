package com.utechia.domain.usecases

import com.utechia.domain.model.EventModel

interface EventUseCase {

    suspend fun execute(status:String):MutableList<EventModel>
    suspend fun get(id:Int):MutableList<EventModel>
    suspend fun apply(id:Int):MutableList<EventModel>

}