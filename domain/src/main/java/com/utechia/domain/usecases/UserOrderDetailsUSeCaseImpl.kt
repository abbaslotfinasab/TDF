package com.utechia.domain.usecases

import com.utechia.domain.model.UserOrderDataModel
import com.utechia.domain.repository.UserOrderDetailsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderDetailsUSeCaseImpl @Inject constructor(private val userOrderRepo: UserOrderDetailsRepo):UserOrderDetailsUseCase<UserOrderDataModel>{



    override suspend fun cancel(id:Int): MutableList<UserOrderDataModel> {
        return userOrderRepo.cancelOrder(id)
    }

    override suspend fun singleOrder(id: Int): MutableList<UserOrderDataModel> {
        return userOrderRepo.singleOrder(id)
    }

    override suspend fun rate(order: Int, rate: Int): MutableList<UserOrderDataModel>{
        return userOrderRepo.rate(order,rate)
    }

}