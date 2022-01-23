package com.utechia.domain.usecases

import com.utechia.domain.model.EventModel
import com.utechia.domain.repository.EventDetailRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventDetailsUsCaseImpl @Inject constructor(private val eventDetailRepo: EventDetailRepo)
    :EventDetailsUseCase {

    override suspend fun get(id: Int): EventModel {
        return eventDetailRepo.getEventByID(id)
    }


}