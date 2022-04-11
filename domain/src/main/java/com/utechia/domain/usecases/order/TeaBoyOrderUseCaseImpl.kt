package com.utechia.domain.usecases.order

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.domain.repository.order.TeaBoyOrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyOrderUseCaseImpl @Inject constructor(private val teaBoyOrderRepo: TeaBoyOrderRepo)
    : TeaBoyOrderUseCase<LiveData<PagingData<TeaBoyOrderDataModel>>> {

    override suspend fun getOrder(status:String): LiveData<PagingData<TeaBoyOrderDataModel>> {
        return teaBoyOrderRepo.getTeaBoyOrder(status)
    }
}