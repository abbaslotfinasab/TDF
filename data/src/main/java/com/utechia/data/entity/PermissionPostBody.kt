package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PermissionPostBody(

    val type:String,
    val description:String

): Parcelable