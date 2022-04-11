package com.utechia.domain.model.refreshment

data class ItemModel(
    val cartId: Int?,
    val createdAt: String?,
    val foodId: String?,
    val id: Int?,
    val food: RefreshmentModel,
    val quantity: Int?,
    val updatedAt: String?
)
