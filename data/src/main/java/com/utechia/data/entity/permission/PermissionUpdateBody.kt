package com.utechia.data.entity.permission

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PermissionUpdateBody(
    val id:Int,
    val status:String
): Parcelable