package com.utechia.domain.model.reservation

data class AnswerReservationModel(
    var subject:String,
    var description:String,
    var date:String,
    var startsAt:String,
    var endsAt:String,
    var attendees:MutableList<Any>?=null,
    var guests: MutableList<Guests>,
    var roomId:Int,

)
