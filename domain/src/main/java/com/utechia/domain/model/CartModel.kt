package com.utechia.domain.model

data class CartModel(
    val createdAt: String?,
    val id: Int?,
    val items: MutableList<ItemModel>?,
    val status: Boolean?,
    val updatedAt: String?
)
