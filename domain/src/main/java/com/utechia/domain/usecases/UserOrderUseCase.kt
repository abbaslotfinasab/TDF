package com.utechia.domain.usecases



interface UserOrderUseCase<R> {

    suspend fun execute(status:String):R

}