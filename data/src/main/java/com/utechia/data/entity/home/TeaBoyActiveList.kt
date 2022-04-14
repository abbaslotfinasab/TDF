package com.utechia.data.entity.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TeaBoyActiveList(

    val list:@Contextual @RawValue MutableList<TeaBoyActiveData>?,

    ): Parcelable