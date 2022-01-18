package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TeaBoyOrder(

    val data :@Contextual @RawValue TeaBoyOrderPage,
    val error:@Contextual @RawValue Error?

): Parcelable