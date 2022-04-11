package com.utechia.data.entity.permission

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Permission(

    val data :@Contextual @RawValue PermissionPage,
    val error:@Contextual @RawValue Error?

): Parcelable