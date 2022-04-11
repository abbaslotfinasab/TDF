package com.utechia.data.entity.refreshment

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Like(

    val data: @Contextual @RawValue Any?,
    val error:@Contextual Error?

): Parcelable