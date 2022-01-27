package com.utechia.domain.usecases



interface UserOrderUseCase<R> {

    suspend fun execute(status:String):MutableList<R>
    suspend fun cancel(id:Int):MutableList<R>
    suspend fun singleOrder(id:Int):MutableList<R>
    suspend fun rate(order:Int,rate:Int):MutableList<R>


}