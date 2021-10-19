package com.utechia.domain.model

data class RoomModel(

     var id:Int?,

     var name:String?,

     var capacity:Int?,

     var hour:MutableList<HourModel>

)
