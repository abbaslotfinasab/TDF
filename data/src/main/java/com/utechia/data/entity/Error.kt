package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Error(

    val message: List<String>?,
    val path: String?,
    val statusCode: Int?,
    val timestamp: String?

):Parcelable