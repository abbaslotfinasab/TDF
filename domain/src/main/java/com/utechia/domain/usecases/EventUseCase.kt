package com.utechia.domain.usecases

interface EventUseCase<R> {

    suspend fun execute():MutableList<R>
    suspend fun apply(id:Int):MutableList<R>

}