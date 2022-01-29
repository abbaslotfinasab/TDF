package com.utechia.domain.repository

import com.utechia.domain.model.CartModel
import com.utechia.domain.model.OrderBodyModel

interface CartRepo {

    suspend fun getCart():MutableList<CartModel>
    suspend fun postCart(id:Int,quantity:Int)
    suspend fun updateCart(id:Int,quantity:Int)
    suspend fun deleteCart(id:Int):MutableList<CartModel>
    suspend fun checkout(): MutableList<CartModel>


}