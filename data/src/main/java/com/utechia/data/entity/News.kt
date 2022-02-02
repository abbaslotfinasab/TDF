package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class News(
    val title: String? = "",
    val link: String? = "",
    val date: String = "",
):Parcelable

