package com.utechia.domain.repository.reservation

import com.utechia.domain.model.reservation.SingleMeetingModel

interface SingleMeetingRepo {
    suspend fun getSingle(id:Int):SingleMeetingModel
}