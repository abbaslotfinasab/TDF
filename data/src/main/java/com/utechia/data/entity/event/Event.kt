package com.utechia.data.entity.event

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Event(

    val data: @Contextual EventPage?,
    val error:@Contextual Error?

): Parcelable