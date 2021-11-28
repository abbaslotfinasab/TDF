package com.utechia.domain.usecases



interface OrderUseCase<R> {

    suspend fun execute(status:String):MutableList<R>
    suspend fun cancel(id:Int)
    suspend fun singleOrder(id:Int):MutableList<R>
    suspend fun acceptOrder(id:Int)
    suspend fun rejectOrder(id:Int)
    suspend fun deliveredOrder(id:Int)
    suspend fun getOrder(status:String):MutableList<R>
    suspend fun singleOrderTeaBoy(id:Int):MutableList<R>

}