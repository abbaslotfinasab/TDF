package com.utechia.domain.usecases


interface OrderCountUseCase<R> {


    suspend fun getOrderCount(): MutableList<R>

    suspend fun setStatus(status:Boolean): MutableList<R>

}