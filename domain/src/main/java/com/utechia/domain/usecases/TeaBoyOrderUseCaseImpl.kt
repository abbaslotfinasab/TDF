package com.utechia.domain.usecases

import com.utechia.domain.model.TeaBoyOrderDataModel
import com.utechia.domain.repository.TeaBoyOrderRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyOrderUseCaseImpl @Inject constructor(private val teaBoyOrderRepo: TeaBoyOrderRepo)
    :TeaBoyOrderUseCase<TeaBoyOrderDataModel>{

    override suspend fun getOrder(status:String): MutableList<TeaBoyOrderDataModel> {
        return teaBoyOrderRepo.getTeaBoyOrder(status)
    }

    override suspend fun acceptOrder(id: Int): MutableList<TeaBoyOrderDataModel> {
        return teaBoyOrderRepo.acceptOrder(id)
    }

    override suspend fun rejectOrder(id: Int): MutableList<TeaBoyOrderDataModel> {
        return teaBoyOrderRepo.rejectOrder(id)
    }

    override suspend fun deliveredOrder(id: Int):MutableList<TeaBoyOrderDataModel>{
        return teaBoyOrderRepo.deliveredOrder(id)
    }

    override suspend fun singleOrderTeaBoy(id: Int): MutableList<TeaBoyOrderDataModel> {
        return teaBoyOrderRepo.singleOrderTeaBoy(id)
    }

}