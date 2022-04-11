package com.utechia.domain.usecases.order


interface TeaBoyOrderUseCase<R> {
    suspend fun getOrder(status:String):R

}