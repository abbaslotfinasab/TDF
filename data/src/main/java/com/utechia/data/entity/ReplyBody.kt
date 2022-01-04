package com.utechia.data.entity

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReplyBody(

    val id: Int?,
    val mediaurl: List<Uri>?,
    val text: String?,

):Parcelable
