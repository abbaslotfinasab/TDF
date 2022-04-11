package com.utechia.data.entity.order

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OrderCount(

    val data: @Contextual @RawValue MutableList<OrderCountData>?,
    val error:  @Contextual @RawValue Error?

):Parcelable
