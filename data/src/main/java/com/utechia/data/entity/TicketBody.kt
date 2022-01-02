package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TicketBody(

    val Priority: String?,
    val category: Int?,
    val description: String?,
    val floor: String?,
    val mediaurl: List<String>?,
    val title: String?,

):Parcelable