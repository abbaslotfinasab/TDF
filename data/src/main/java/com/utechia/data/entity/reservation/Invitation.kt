package com.utechia.data.entity.reservation

import android.os.Parcelable
import com.utechia.data.entity.profile.ProfileData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Invitation(
    val data: @Contextual @RawValue MutableList<ProfileData>?,
    val error:  @Contextual @RawValue Any?
): Parcelable
