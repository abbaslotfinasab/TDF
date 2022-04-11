package com.utechia.domain.model.order

import com.utechia.domain.model.cart.CartModel
import com.utechia.domain.model.profile.UserModel

data class TeaBoyOrderDataModel(

    val id: Int?,
    val status: String?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val location:String?,
    val cart: CartModel?,
    val user: UserModel?,

    )