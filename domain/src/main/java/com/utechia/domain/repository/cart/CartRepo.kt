package com.utechia.domain.repository.cart

import com.utechia.domain.model.cart.CartModel

interface CartRepo {

    suspend fun getCart():MutableList<CartModel>
    suspend fun postCart(id:Int,quantity:Int)
    suspend fun updateCart(id:Int,quantity:Int)
    suspend fun deleteCart(id:Int):MutableList<CartModel>
    suspend fun checkout(location:String,floor:Int): MutableList<CartModel>


}