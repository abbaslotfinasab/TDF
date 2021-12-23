package com.utechia.domain.usecases

import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.repository.TeaBoyOrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyOrderUseCaseImpl @Inject constructor(private val teaBoyOrderRepo: TeaBoyOrderRepo)
    :TeaBoyOrderUseCase<OrderDataModel>{

    override suspend fun getOrder(status:String): MutableList<OrderDataModel> {
        return teaBoyOrderRepo.getTeaBoyOrder(status)
    }

    override suspend fun acceptOrder(id: Int): MutableList<OrderDataModel> {
        return teaBoyOrderRepo.acceptOrder(id)
    }

    override suspend fun rejectOrder(id: Int): MutableList<OrderDataModel> {
        return teaBoyOrderRepo.rejectOrder(id)
    }

    override suspend fun deliveredOrder(id: Int){
        return teaBoyOrderRepo.deliveredOrder(id)
    }

    override suspend fun singleOrderTeaBoy(id: Int): MutableList<OrderDataModel> {
        return teaBoyOrderRepo.singleOrderTeaBoy(id)
    }

}