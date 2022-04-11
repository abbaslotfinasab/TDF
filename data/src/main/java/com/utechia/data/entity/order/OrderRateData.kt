package com.utechia.data.entity.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OrderRateData(
    val success: Boolean
):Parcelable