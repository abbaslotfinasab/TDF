package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PermissionType(

    val data: @Contextual MutableList<String>?,
    val error:@Contextual Error?

): Parcelable