package com.utechia.domain.model

data class OrderDataModel(

    val id: Int?,
    val status: String?,
    var orderrate: MutableList<RatingModel>?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart:CartModel?,

    )