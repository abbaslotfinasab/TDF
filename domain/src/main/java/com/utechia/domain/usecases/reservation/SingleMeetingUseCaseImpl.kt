package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.SingleMeetingModel
import com.utechia.domain.repository.reservation.SingleMeetingRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleMeetingUseCaseImpl @Inject constructor(private val singleMeetingRepo: SingleMeetingRepo):SingleMeetingUseCase {
    override suspend fun execute(id: Int): SingleMeetingModel {
        return singleMeetingRepo.getSingle(id)
    }
}