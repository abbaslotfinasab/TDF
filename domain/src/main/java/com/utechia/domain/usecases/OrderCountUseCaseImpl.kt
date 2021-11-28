package com.utechia.domain.usecases

import com.utechia.domain.model.OrderCountModel
import com.utechia.domain.repository.OrderCountRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderCountUseCaseImpl @Inject constructor(private val orderCountRepo: OrderCountRepo)
    :OrderCountUseCase<OrderCountModel>{
    override suspend fun getOrderCount(): MutableList<OrderCountModel> {
        return orderCountRepo.getOrderCount()
    }

    override suspend fun setStatus(status: Boolean) {
        return orderCountRepo.setStatus(status)
    }
}