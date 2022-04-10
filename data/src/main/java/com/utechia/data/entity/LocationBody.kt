package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationBody(

    val floorId:Int,
    val location:String,

): Parcelable