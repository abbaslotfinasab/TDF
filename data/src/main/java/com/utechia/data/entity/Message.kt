package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Message(
    val statusCode: Int,
    val message: List<String>,
    val error: String,

    ):Parcelable