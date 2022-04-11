package com.utechia.data.entity.ticket

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.ticket.UploadModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UploadData(
    val path: String?,
):Parcelable,ResponseObject<UploadModel> {
    override fun toDomain(): UploadModel {
        return UploadModel(path)
    }
}