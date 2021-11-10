package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Message(
    val statusCode: Int,
    val message: List<String>,
    val error: String,

    ):Parcelable