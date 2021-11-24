package com.utechia.domain.model


data class OrderDataModel(

    val id: Int?,
    val status: String?,
    val rating: Int?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart:CartModel,

    )