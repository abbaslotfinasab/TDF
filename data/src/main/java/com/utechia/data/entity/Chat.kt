package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Chat(

    val UserRol: String? = "",
    val datetime: String? = "",
    val fid: String? = "",
    val isreceive: Boolean ? = false,
    val mediaurl: List<String> ? = emptyList(),
    val senderid: Long? = 0,
    val text: String? = "",
    val ticket: Long? = 0,
    val ticketfid: String? = "",
    val username: String? = ""

    ):Parcelable