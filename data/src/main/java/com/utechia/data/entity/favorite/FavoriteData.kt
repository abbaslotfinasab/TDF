package com.utechia.data.entity.favorite

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.data.entity.refreshment.RefreshmentData
import com.utechia.domain.model.favorite.FavoriteModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class FavoriteData(
    val createdAt: String?,
    val food: RefreshmentData?,
    val id: Int?,
    val updatedAt: String?,
):Parcelable,ResponseObject<FavoriteModel> {
    override fun toDomain(): FavoriteModel {
        return FavoriteModel(createdAt,food?.toDomain(),id,updatedAt)
    }
}