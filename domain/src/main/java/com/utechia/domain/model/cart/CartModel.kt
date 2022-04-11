package com.utechia.domain.model.cart

import com.utechia.domain.model.refreshment.ItemModel

data class CartModel(
    val createdAt: String?,
    val id: Int?,
    val items: MutableList<ItemModel>?,
    val status: Boolean?,
    val updatedAt: String?
)
