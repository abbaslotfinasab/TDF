package com.utechia.data.entity.ticket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RateTicketBody(

    val rate:Int,
    val ticket:Int

): Parcelable