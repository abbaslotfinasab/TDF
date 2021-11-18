package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.RefreshmentModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class RefreshmentData(

    val category: String?,
    val createdAt: String?,
    val id: Int?,
    val imageName: String?,
    val imagePath: String?,
    val rating: Int?,
    val status: Boolean?,
    val title: String?,
    val updatedAt: String?

):Parcelable , ResponseObject<RefreshmentModel> {
    override fun toDomain(): RefreshmentModel {
        return RefreshmentModel(category,createdAt,id,imageName,imagePath,rating,status,title,updatedAt,false)
    }
}