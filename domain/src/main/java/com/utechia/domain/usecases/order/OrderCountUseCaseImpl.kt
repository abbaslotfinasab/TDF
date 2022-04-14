package com.utechia.domain.usecases.order

import com.utechia.domain.model.order.OrderCountModel
import com.utechia.domain.repository.order.OrderCountRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderCountUseCaseImpl @Inject constructor(private val orderCountRepo: OrderCountRepo)
    : OrderCountUseCase<OrderCountModel> {
    override suspend fun getOrderCount(): MutableList<OrderCountModel> {
        return orderCountRepo.getOrderCount()
    }

    override suspend fun setStatus(status: Boolean,floorId:Int): MutableList<OrderCountModel> {
        return orderCountRepo.setStatus(status,floorId)
    }
}