package com.utechia.domain.repository.reservation

import com.utechia.domain.model.reservation.TimeModel

interface TimeRepo {

    suspend fun getTimeMeeting(date:String,id:Int):MutableList<TimeModel>

}