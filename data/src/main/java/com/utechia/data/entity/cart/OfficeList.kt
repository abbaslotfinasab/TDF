package com.utechia.data.entity.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class OfficeList (

    val myRoom:MyRoom?,
    val list: MutableList<OfficeData>?

        ): Parcelable