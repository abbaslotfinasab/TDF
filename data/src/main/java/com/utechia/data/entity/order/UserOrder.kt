package com.utechia.data.entity.order

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserOrder(

    val data :@Contextual @RawValue UserOrderPage,
    val error:@Contextual @RawValue Error?

): Parcelable