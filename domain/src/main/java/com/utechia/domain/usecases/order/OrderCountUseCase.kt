package com.utechia.domain.usecases.order


interface OrderCountUseCase<R> {


    suspend fun getOrderCount(): MutableList<R>

    suspend fun setStatus(status:Boolean,floorId:Int): MutableList<R>

}