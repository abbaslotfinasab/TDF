package com.utechia.data.entity.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationBody(

    val floorId:Int,
    val location:String,

): Parcelable