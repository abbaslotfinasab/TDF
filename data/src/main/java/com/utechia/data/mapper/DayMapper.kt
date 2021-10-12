package com.utechia.data.mapper

import com.utechia.data.entity.Day
import com.utechia.data.entity.Hour
import com.utechia.domain.moodel.DayModel
import com.utechia.domain.moodel.HourModel
import javax.inject.Inject

class DayMapper @Inject constructor() {

    fun toMapper (day: Day): DayModel {

        return DayModel(

            day.id?:0,

            day.weekDay?:"",

            day.month_id?:0,

            )
    }
}