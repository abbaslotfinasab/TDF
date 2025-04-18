package com.utechia.data.entity.notification

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.main.NotificationCountModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class NotificationCountData(

    val pending_orders: Int?,
    val unread_notifications: Int?

):Parcelable , ResponseObject<NotificationCountModel> {
    override fun toDomain(): NotificationCountModel {
        return NotificationCountModel(pending_orders,unread_notifications)
    }
}