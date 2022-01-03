package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class BaseNeedsData(

    val category: @Contextual @RawValue MutableList<Category>?,
    val ListFloor: ArrayList<String>?,
    val Priority: ArrayList<String>?,

    ):Parcelable
