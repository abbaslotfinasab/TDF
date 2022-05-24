package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.TimeModel
import com.utechia.domain.repository.reservation.TimeRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeUseCaseImpl @Inject constructor(private val timeRepo: TimeRepo):TimeUseCase<TimeModel> {
    override suspend fun execute(roomId: Int, date: String): MutableList<TimeModel> {
        return timeRepo.getTimeMeeting(date,roomId)
    }
}