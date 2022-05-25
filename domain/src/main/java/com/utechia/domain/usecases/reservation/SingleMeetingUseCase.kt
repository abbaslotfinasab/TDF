package com.utechia.domain.usecases.reservation

import com.utechia.domain.model.reservation.SingleMeetingModel

interface SingleMeetingUseCase{
    suspend fun execute(id:Int):SingleMeetingModel
}