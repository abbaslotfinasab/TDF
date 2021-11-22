package com.utechia.domain.model

data class ItemModel(
    val cartId: Int?,
    val createdAt: String?,
    val foodId: String?,
    val id: Int?,
    val food: RefreshmentModel,
    val quantity: Int?,
    val updatedAt: String?
)
