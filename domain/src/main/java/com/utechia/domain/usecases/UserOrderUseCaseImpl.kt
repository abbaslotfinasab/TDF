package com.utechia.domain.usecases

import com.utechia.domain.model.UserOrderDataModel
import com.utechia.domain.repository.UserOrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderUseCaseImpl @Inject constructor(private val userOrderRepo: UserOrderRepo):UserOrderUseCase<UserOrderDataModel> {

    override suspend fun execute(status:String): MutableList<UserOrderDataModel> {
        return userOrderRepo.getOrder(status)
    }

    override suspend fun cancel(id:Int): MutableList<UserOrderDataModel>  {
        return userOrderRepo.cancelOrder(id)
    }

    override suspend fun singleOrder(id: Int): MutableList<UserOrderDataModel> {
        return userOrderRepo.singleOrder(id)
    }

    override suspend fun rate(order: Int, rate: Int): MutableList<UserOrderDataModel> {
        return userOrderRepo.rate(order,rate)
    }

}