package com.utechia.domain.usecases

interface OrderRateUseCase {

    suspend fun execute(order:Int,rate:Int):Boolean
}