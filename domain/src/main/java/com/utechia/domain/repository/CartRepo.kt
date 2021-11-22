package com.utechia.domain.repository

import com.utechia.domain.model.CartDataModel

interface CartRepo {

    suspend fun getCart():MutableList<CartDataModel>
    suspend fun postCart(id:Int,quantity:Int)
    suspend fun updateCart(id:Int,quantity:Int)
    suspend fun deleteCart(id:Int)

}