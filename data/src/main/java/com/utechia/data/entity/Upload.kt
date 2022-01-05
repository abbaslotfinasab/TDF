package com.utechia.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Upload(

    val data :@Contextual UploadData?,
    val error:@Contextual Error?

): Parcelable