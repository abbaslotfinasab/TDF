package com.utechia.data.entity.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationBody(
    val id:Int,
    val readAll:Boolean
): Parcelable