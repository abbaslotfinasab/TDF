package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.NotificationModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class NotificationData (
    val id:Int?,
    val type:String?,
    val referenceId:Int?,
    val title:String?,
    val body:String?,
    val isRead:Boolean?,
    val createdAt:String?,
    val updatedAt:String?,
    val access:String?,

    ): Parcelable,ResponseObject<NotificationModel> {
    override fun toDomain(): NotificationModel {
        return NotificationModel(id,type,referenceId,title,body,isRead,createdAt,updatedAt,access)
    }
}