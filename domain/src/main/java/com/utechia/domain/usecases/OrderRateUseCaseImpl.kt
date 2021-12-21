package com.utechia.domain.usecases

import com.utechia.domain.repository.OrderRateRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRateUseCaseImpl @Inject constructor(private val orderRateRepo: OrderRateRepo):OrderRateUseCase {

    override suspend fun execute(order: Int, rate: Int): Boolean {
        return orderRateRepo.rate(order,rate)
    }

}