package com.utechia.domain.usecases.reservation

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.reservation.MeetingModel

interface MeetingUseCase {

    suspend fun execute(status:String): LiveData<PagingData<MeetingModel>>

}