package com.utechia.data.entity.home

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class TeaBoyActive (

    val data :@Contextual @RawValue TeaBoyActiveList?,
    val error:@Contextual @RawValue Error?
): Parcelable