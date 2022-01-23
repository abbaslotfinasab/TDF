package com.utechia.domain.usecases

interface EventUseCase<R> {

    suspend fun execute(status:String):MutableList<R>
    suspend fun get(id:Int):MutableList<R>
    suspend fun apply(id:Int):MutableList<R>

}