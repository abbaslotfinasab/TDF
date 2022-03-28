package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationBody(

    val floor:String,
    val location:String,

): Parcelable