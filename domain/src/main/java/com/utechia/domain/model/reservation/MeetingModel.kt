package com.utechia.domain.model.reservation

data class MeetingModel (
    val id:Int?,
    val subject:String?,
    val description:String?,
    val room:RoomModel?,
    val date:String?,
    val startsAt:String?,
    val endsAt:String?,
    val duration:Int?,
    val status:String?,
)