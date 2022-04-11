package com.utechia.data.entity.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RateEvent(

    val rate:Int,
    val event:Int

): Parcelable