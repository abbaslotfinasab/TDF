package com.utechia.data.entity.main

import android.os.Parcelable
import com.utechia.data.entity.ticket.Message
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Error(
    val message: Message?,
):Parcelable