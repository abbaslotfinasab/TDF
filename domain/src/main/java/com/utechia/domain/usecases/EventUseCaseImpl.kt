package com.utechia.domain.usecases

import com.utechia.domain.model.EventModel
import com.utechia.domain.repository.EventRepo


class EventUseCaseImpl(private val eventRepo: EventRepo):EventUseCase<EventModel> {
    override suspend fun execute(status:String): MutableList<EventModel> {
        return eventRepo.getEvent(status)
    }

    override suspend fun apply(id: Int): MutableList<EventModel> {
        return eventRepo.apply(id)
    }

    override suspend fun get(id: Int): MutableList<EventModel> {
        return eventRepo.getEventByID(id)
    }
}