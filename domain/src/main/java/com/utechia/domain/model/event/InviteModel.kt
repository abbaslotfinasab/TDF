package com.utechia.domain.model.event

data class InviteModel(

    var id:Int?,

    var name:String?,

    var image:String?,

    var profession:String?,

    var invited:Boolean = false

)
