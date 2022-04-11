package com.utechia.data.entity.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class EventPage(

    val totalPages: Int?,
    val list:@Contextual @RawValue MutableList<EventData>?,

    ): Parcelable