package com.utechia.data.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VerifyData(

    val token: String?,
    val userHomeId: String?

):Parcelable