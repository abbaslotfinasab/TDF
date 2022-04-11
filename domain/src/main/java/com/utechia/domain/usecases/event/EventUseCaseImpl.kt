package com.utechia.domain.usecases.event

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.event.EventModel
import com.utechia.domain.repository.event.EventRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventUseCaseImpl @Inject constructor(private val eventRepo: EventRepo)
    : EventUseCase<LiveData<PagingData<EventModel>>> {
    override suspend fun execute(status: String): LiveData<PagingData<EventModel>> {
        return eventRepo.getEvent(status)
    }




}