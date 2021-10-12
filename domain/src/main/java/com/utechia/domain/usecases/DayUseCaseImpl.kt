package com.utechia.domain.usecases

import com.utechia.domain.moodel.DayModel
import com.utechia.domain.repository.DayRepo
import javax.inject.Inject

class DayUseCaseImpl @Inject constructor(private val dayRepo: DayRepo):DayUseCase<DayModel> {

    override suspend fun execute(month_id:Int): MutableList<DayModel> {
        return dayRepo.getDay(month_id)
    }
}