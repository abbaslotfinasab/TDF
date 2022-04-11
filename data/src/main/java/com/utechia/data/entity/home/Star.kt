package com.utechia.data.entity.home

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Star(

    val data: @Contextual StarData?,
    val error:@Contextual Error?

): Parcelable