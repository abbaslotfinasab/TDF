package com.utechia.domain.model.order

import com.utechia.domain.model.cart.CartModel

data class UserOrderDataModel(

    val id: Int?,
    val status: String?,
    var orderrate: MutableList<RatingModel>?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val cart: CartModel?,
    val location:String?,


    )