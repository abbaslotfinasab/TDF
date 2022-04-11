package com.utechia.domain.model.notification

data class NotificationModel(

    val id:Int?,
    val type:String?,
    val referenceId:Int?,
    val title:String?,
    val body:String?,
    var isRead:Boolean?,
    val createdAt:String?,
    val updatedAt:String?,
    val access:String?,


    )
