package com.utechia.domain.usecases

interface RefreshmentUseCase<R> {

    suspend fun get(type:String):MutableList<R>
    suspend fun search(search:String,type:String):MutableList<R>
    suspend fun cart(id:Int):MutableList<R>
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)

}