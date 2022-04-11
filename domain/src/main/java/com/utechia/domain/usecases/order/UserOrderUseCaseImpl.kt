package com.utechia.domain.usecases.order

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.order.UserOrderDataModel
import com.utechia.domain.repository.profile.UserOrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderUseCaseImpl @Inject constructor(private val userOrderRepo: UserOrderRepo):
    UserOrderUseCase<LiveData<PagingData<UserOrderDataModel>>> {

    override suspend fun execute(status:String): LiveData<PagingData<UserOrderDataModel>> {
        return userOrderRepo.getOrder(status)
    }
}