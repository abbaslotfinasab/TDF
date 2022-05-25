package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import com.utechia.data.entity.notification.NotificationPage
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Meeting(

    val data :@Contextual @RawValue MeetingPages?,
    val error:@Contextual @RawValue Error?

): Parcelable