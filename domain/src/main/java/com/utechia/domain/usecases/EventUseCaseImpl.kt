package com.utechia.domain.usecases

import com.utechia.domain.model.EventModel
import com.utechia.domain.repository.EventRepo
import javax.inject.Singleton

@Singleton
class EventUseCaseImpl(private val eventRepo: EventRepo):EventUseCase<EventModel> {
    override suspend fun execute(): MutableList<EventModel> {
        return eventRepo.getEvent()
    }

    override suspend fun apply(id: Int): MutableList<EventModel> {
        return eventRepo.apply(id)
    }
}