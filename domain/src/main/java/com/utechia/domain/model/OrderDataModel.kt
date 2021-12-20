package com.utechia.domain.model

import kotlinx.parcelize.RawValue


data class OrderDataModel(

    val id: Int?,
    val status: String?,
    val orderrate: MutableList<RatingModel>,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart:CartModel,

    )