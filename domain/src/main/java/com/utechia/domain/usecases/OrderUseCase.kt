package com.utechia.domain.usecases

import com.utechia.domain.model.OrderDataModel


interface OrderUseCase<R> {

    suspend fun execute(status:String):MutableList<R>
    suspend fun cancel(id:Int)
    suspend fun singleOrder(id:Int):MutableList<R>

}