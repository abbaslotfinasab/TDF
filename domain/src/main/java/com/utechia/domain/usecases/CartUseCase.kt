package com.utechia.domain.usecases


interface CartUseCase<R> {

    suspend fun getCart():MutableList<R>
    suspend fun postCart(id:Int,quantity:Int)
    suspend fun updateCart(id:Int,quantity:Int)
    suspend fun deleteCart(id:Int)

}