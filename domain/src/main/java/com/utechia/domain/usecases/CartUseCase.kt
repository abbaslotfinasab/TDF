package com.utechia.domain.usecases

import com.utechia.domain.model.CartModel


interface CartUseCase {

    suspend fun getCart():MutableList<CartModel>
    suspend fun postCart(id:Int,quantity:Int)
    suspend fun updateCart(id:Int,quantity:Int)
    suspend fun deleteCart(id:Int):MutableList<CartModel>
    suspend fun execute(location:String,floor:Int): MutableList<CartModel>


}