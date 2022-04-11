package com.utechia.domain.model.reservation

import com.utechia.domain.model.reservation.HourModel

data class RoomModel(

     var id:Int?,

     var name:String?,

     var floor:String?,

     var capacity:Int?,

     var hour:MutableList<HourModel>

)
