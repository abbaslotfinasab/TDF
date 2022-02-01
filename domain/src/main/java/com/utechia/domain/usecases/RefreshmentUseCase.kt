package com.utechia.domain.usecases


interface RefreshmentUseCase<R> {

    suspend fun get(type:String):MutableList<R>
    suspend fun search(search:String,type:String):MutableList<R>
    suspend fun cart():MutableList<R>
    suspend fun postCart(id:Int,quantity:Int)
    suspend fun updateCart(id:Int,quantity:Int)
    suspend fun deleteCart(id:Int)
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)


}