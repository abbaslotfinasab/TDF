package com.utechia.data.entity.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class NotificationPage(

    val totalPages: Int?,
    val list:@Contextual @RawValue MutableList<NotificationData>?,

    ): Parcelable