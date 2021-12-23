package com.utechia.domain.usecases

import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.repository.UserOrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderUseCaseImpl @Inject constructor(private val userOrderRepo: UserOrderRepo):OrderUseCase<OrderDataModel> {

    override suspend fun execute(status:String): MutableList<OrderDataModel> {
        return userOrderRepo.getOrder(status)
    }

    override suspend fun cancel(id:Int): MutableList<OrderDataModel>  {
        return userOrderRepo.cancelOrder(id)
    }

    override suspend fun singleOrder(id: Int): MutableList<OrderDataModel> {
        return userOrderRepo.singleOrder(id)
    }
}