package com.utechia.domain.usecases.event

import com.utechia.domain.model.event.EventModel
import com.utechia.domain.repository.event.EventDetailRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventDetailsUsCaseImpl @Inject constructor(private val eventDetailRepo: EventDetailRepo)
    : EventDetailsUseCase<EventModel> {

    override suspend fun get(id: Int): MutableList<EventModel>  {
        return eventDetailRepo.getEventByID(id)
    }


    override suspend fun apply(id: Int): MutableList<EventModel> {
        return eventDetailRepo.apply(id)
    }

    override suspend fun cancel(id: Int): MutableList<EventModel> {
        return eventDetailRepo.cancel(id)
    }

    override suspend fun rate(id: Int,rate:Int): MutableList<EventModel> {
        return eventDetailRepo.rate(id,rate)
    }


}