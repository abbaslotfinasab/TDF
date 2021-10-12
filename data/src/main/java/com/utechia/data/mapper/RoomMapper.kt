package com.utechia.data.mapper

import com.utechia.data.entity.Room
import com.utechia.domain.moodel.RoomModel
import javax.inject.Inject

class RoomMapper @Inject constructor() {

    fun toMapper (roomModel: Room): RoomModel{

        return RoomModel(

            roomModel.id?:0,

            roomModel.name?:"",

            roomModel.capacity?:0,

            roomModel.image?:"",

            roomModel.day_id?:0,

            roomModel.month_id?:0,

           /* roomModel.hour.map { HourMapper().toMapper(it) }.toMutableList()*/

            )
    }
}