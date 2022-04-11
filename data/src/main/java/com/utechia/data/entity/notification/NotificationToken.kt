package com.utechia.data.entity.notification

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationToken(

    val token:String

): Parcelable