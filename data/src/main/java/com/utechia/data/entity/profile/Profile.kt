package com.utechia.data.entity.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Profile(
    val data: @Contextual @RawValue ProfileData?,
    val error:  @Contextual @RawValue Any?
): Parcelable
