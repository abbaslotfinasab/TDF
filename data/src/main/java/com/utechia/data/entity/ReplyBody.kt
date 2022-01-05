package com.utechia.data.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import org.json.JSONArray

@Parcelize
data class ReplyBody(

    val ticketid: Int?,
    val mediaurl: @RawValue JSONArray,
    val text: String?,

    ):Parcelable
