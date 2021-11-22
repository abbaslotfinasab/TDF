package com.utechia.data.entity

import android.os.Parcelable

import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Cart(

    val data :@Contextual @RawValue MutableList<CartData>?,
    val error:@Contextual @RawValue Error?

):Parcelable