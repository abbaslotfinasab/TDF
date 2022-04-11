package com.utechia.domain.usecases.order

import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.domain.repository.order.TeaBoyOrderDetailsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyOrderDetailsUSeCaseImpl @Inject constructor(private val teaBoyOrderRepo: TeaBoyOrderDetailsRepo)
    : TeaBoyOrderDetailsUseCase<TeaBoyOrderDataModel> {

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