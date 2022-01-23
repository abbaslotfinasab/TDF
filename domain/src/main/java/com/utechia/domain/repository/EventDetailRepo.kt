package com.utechia.domain.repository

import com.utechia.domain.model.EventModel

interface EventDetailRepo {

    suspend fun getEventByID(id: Int):EventModel

}