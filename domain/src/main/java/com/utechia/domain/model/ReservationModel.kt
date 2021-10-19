package com.utechia.domain.model

data class ReservationModel(

    var id:Int?,

    var title:String?,

    var capacity :Int?,

    var room_id:Int?,

    var day:String?,

    var month:String?,

    var year:String?,

    var starTime:String?,

    var endTime:String?,

    var duration:String?,

    var description:String?,

    var invite: MutableList<InviteModel>

)
