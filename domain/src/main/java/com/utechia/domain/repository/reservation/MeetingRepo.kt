package com.utechia.domain.repository.reservation

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.reservation.MeetingModel

interface MeetingRepo {
    suspend fun getAllMeeting(status:String): LiveData<PagingData<MeetingModel>>
}