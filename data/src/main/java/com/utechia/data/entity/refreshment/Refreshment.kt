package com.utechia.data.entity.refreshment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Refreshment(
    val data: @Contextual @RawValue MutableList<RefreshmentData>?,
    val error:  @Contextual @RawValue Any?
) : Parcelable