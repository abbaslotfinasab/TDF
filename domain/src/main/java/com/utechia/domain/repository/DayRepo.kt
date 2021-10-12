package com.utechia.domain.repository

import com.utechia.domain.moodel.DayModel

interface DayRepo {

    suspend fun getDay(month_id:Int):MutableList<DayModel>

}