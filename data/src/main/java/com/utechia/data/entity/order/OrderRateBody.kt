package com.utechia.data.entity.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OrderRateBody(
    val order:Int,
    val rate:Int
): Parcelable