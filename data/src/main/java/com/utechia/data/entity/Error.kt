package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Error(

    val message: List<String>?,
    val path: String?,
    val statusCode: Int?,
    val timestamp: String?

):Parcelable