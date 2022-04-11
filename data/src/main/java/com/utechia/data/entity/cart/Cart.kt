package com.utechia.data.entity.cart

import android.os.Parcelable
import com.utechia.data.entity.main.Error

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