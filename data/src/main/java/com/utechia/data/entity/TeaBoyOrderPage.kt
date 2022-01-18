package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TeaBoyOrderPage(

    val totalPages: Int?,
    val list:@Contextual @RawValue MutableList<TeaBoyOrderData>?,

    ): Parcelable