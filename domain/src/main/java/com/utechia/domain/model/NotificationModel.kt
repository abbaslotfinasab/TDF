package com.utechia.domain.model

data class NotificationModel(

    val id:Int?,
    val type:String?,
    val referenceId:Int?,
    val title:String?,
    val body:String?,
    val isRead:Boolean?,
    val createdAt:String?,
    val updatedAt:String?,

)
