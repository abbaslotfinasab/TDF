package com.utechia.domain.model

import kotlinx.parcelize.RawValue

data class CartDataModel(

    val createdAt: String,
    val id: Int,
    val items: MutableList<ItemModel>,
    val status: Boolean,
    val updatedAt: String


)