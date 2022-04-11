package com.utechia.data.entity.event


import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class EventDetails(

    val data: @Contextual EventData?,
    val error:@Contextual Error?

): Parcelable