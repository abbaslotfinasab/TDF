package com.utechia.domain.usecases

interface FavoriteUseCase<R> {

    suspend fun execute():MutableList<R>
    suspend fun exist(title:String):MutableList<R>
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)

}