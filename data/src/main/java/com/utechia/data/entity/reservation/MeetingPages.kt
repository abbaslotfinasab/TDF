package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.entity.notification.NotificationData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MeetingPages(

    val totalPages: Int?,
    val list:@Contextual @RawValue MutableList<MeetingData>?,

    ): Parcelable