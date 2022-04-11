package com.utechia.data.entity.permission

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PermissionType(

    val data: @Contextual MutableList<String>?,
    val error:@Contextual Error?

): Parcelable