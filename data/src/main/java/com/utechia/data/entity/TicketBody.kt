package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketBody(

    val Priority: String?,
    val category: Int?,
    val description: String?,
    val floorId: Int?,
    val mediaurl: MutableSet<String>?,
    val title: String?,

):Parcelable