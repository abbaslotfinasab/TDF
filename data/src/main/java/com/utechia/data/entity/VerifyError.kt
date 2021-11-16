package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class VerifyError(
    val message: @Contextual Message
):Parcelable