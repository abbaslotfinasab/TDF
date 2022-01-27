package com.utechia.domain.usecases


interface TeaBoyOrderUseCase<R> {

    suspend fun acceptOrder(id:Int):MutableList<R>
    suspend fun rejectOrder(id:Int):MutableList<R>
    suspend fun deliveredOrder(id:Int):MutableList<R>
    suspend fun getOrder(status:String):MutableList<R>
    suspend fun singleOrderTeaBoy(id:Int):MutableList<R>

}