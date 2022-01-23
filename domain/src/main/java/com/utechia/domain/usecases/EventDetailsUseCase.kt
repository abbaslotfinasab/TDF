package com.utechia.domain.usecases

import com.utechia.domain.model.EventModel

interface EventDetailsUseCase {

    suspend fun get(id: Int): EventModel
}
