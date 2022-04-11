package com.utechia.domain.usecases.order



interface UserOrderUseCase<R> {

    suspend fun execute(status:String):R

}