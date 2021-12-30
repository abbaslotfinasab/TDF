package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Ticket(
    val title: String? = "",
    val adminname: String? = "",
    val datetime: String? = "",
    val fid: Long? = 0,
    val id: Long? = 0,
    val isreceive: Boolean? = false,
    val mediaurl: List<String>? = emptyList(),
    val text: String? = "",
    val ticket: Long? = 0,
    val ticketfid: Long? = 0,
    val username: String? = ""
):Parcelable