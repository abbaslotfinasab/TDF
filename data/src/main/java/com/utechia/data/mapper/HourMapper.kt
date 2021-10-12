package com.utechia.data.mapper

import com.utechia.data.entity.Hour
import com.utechia.domain.moodel.HourModel
import javax.inject.Inject

class HourMapper @Inject constructor() {

    fun toMapper (hour: Hour): HourModel {

        return HourModel(

            hour.id?:0,

            hour.title?:"",

            hour.reserve?:false,

        )
    }
}