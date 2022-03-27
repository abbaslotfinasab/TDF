package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeleteNotificationBody(
    val id:Int,
    val deleteAll:Boolean
): Parcelable