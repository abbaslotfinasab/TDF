package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.CartModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class CartData(
    val createdAt: String,
    val id: Int,
    val items: @Contextual @RawValue MutableList<Item>,
    val status: Boolean,
    val updatedAt: String
):Parcelable, ResponseObject<CartModel> {
    override fun toDomain(): CartModel {
        return CartModel(
            createdAt,
            id,
            items.map { it.toDomain() }.toMutableList(),
            status,
            updatedAt
        )
    }
}