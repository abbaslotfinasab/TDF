package com.utechia.data.entity.ticket

import android.os.Parcelable
import com.utechia.data.entity.main.Error
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Upload(

    val data :@Contextual UploadData?,
    val error:@Contextual Error?

): Parcelable