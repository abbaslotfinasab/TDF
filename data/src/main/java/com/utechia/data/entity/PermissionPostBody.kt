package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PermissionPostBody(

    val type:String,
    val description:String,
    val datestarts:String,
    val dateends : String

): Parcelable