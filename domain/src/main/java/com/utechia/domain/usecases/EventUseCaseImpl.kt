package com.utechia.domain.usecases

import com.utechia.domain.model.EventModel
import com.utechia.domain.repository.EventRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventUseCaseImpl @Inject constructor(private val eventRepo: EventRepo)
    :EventUseCase {
    override suspend fun execute(status: String): MutableList<EventModel> {
        return eventRepo.getEvent(status)
    }



    override suspend fun apply(id: Int): MutableList<EventModel> {
        return eventRepo.apply(id)
    }

}