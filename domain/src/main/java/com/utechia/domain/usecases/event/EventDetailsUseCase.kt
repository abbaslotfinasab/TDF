package com.utechia.domain.usecases.event


interface EventDetailsUseCase<R> {

    suspend fun get(id: Int): MutableList<R>
    suspend fun apply(id:Int):MutableList<R>
    suspend fun cancel(id:Int):MutableList<R>
    suspend fun rate(id:Int,rate:Int):MutableList<R>
}
