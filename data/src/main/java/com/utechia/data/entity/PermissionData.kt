package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.PermissionModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class PermissionData(

    val id: Int?,
    val status: String?,
    val type: String?,
    val description: String?,
    val datestarts: String?,
    val dateends: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val timeLength: String?,

    ):Parcelable,ResponseObject<PermissionModel>{
    override fun toDomain(): PermissionModel {
        return PermissionModel(id,status,type,description,datestarts,dateends,createdAt,updatedAt,timeLength)
    }
}
