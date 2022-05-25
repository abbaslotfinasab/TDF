package com.utechia.domain.usecases.reservation

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.reservation.MeetingModel
import com.utechia.domain.repository.reservation.MeetingRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeetingUseCaseImpl @Inject constructor(private val meetingRepo: MeetingRepo):MeetingUseCase
{
    override suspend fun execute(status: String): LiveData<PagingData<MeetingModel>> {
        return meetingRepo.getAllMeeting(status)
    }
}