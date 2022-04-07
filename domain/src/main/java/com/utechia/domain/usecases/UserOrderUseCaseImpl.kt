package com.utechia.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.UserOrderDataModel
import com.utechia.domain.repository.UserOrderRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderUseCaseImpl @Inject constructor(private val userOrderRepo: UserOrderRepo):UserOrderUseCase<LiveData<PagingData<UserOrderDataModel>>> {

    override suspend fun execute(status:String): LiveData<PagingData<UserOrderDataModel>> {
        return userOrderRepo.getOrder(status)
    }
}