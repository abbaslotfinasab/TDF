package com.utechia.data.entity.reservation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Time(
    val data: @Contextual @RawValue MutableList<TimeData>?,
    val error:  @Contextual @RawValue Any?
): Parcelable
