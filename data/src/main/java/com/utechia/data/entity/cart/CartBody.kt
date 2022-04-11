package com.utechia.data.entity.cart

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartBody(

    val foodId:Int?,
    val quantity:Int?

):Parcelable
