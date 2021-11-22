package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.ItemModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Item(
    val cartId: Int?,
    val createdAt: String?,
    val foodId: String?,
    val id: Int?,
    val food:RefreshmentData,
    val quantity: Int?,
    val updatedAt: String?
):Parcelable,ResponseObject<ItemModel> {
    override fun toDomain(): ItemModel {
        return ItemModel(cartId,createdAt,foodId,id,food.toDomain(),quantity,updatedAt)
    }
}
