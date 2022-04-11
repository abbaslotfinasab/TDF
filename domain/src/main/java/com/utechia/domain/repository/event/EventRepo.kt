package com.utechia.domain.repository.event

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.event.EventModel

interface EventRepo {

    suspend fun getEvent(status:String): LiveData<PagingData<EventModel>>

}