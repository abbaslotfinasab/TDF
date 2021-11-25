package com.utechia.domain.usecases

import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.repository.OrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderUseCaseImpl @Inject constructor(private val orderRepo: OrderRepo):OrderUseCase<OrderDataModel> {

    override suspend fun execute(status:String): MutableList<OrderDataModel> {
        return orderRepo.getOrder(status)
    }

    override suspend fun cancel(id:Int) {
        return orderRepo.cancelOrder(id)
    }

    override suspend fun singleOrder(id: Int): MutableList<OrderDataModel> {
        return orderRepo.singleOrder(id)
    }
}