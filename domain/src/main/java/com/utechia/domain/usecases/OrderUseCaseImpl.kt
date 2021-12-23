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

    override suspend fun cancel(id:Int): MutableList<OrderDataModel>  {
        return orderRepo.cancelOrder(id)
    }

    override suspend fun singleOrder(id: Int): MutableList<OrderDataModel> {
        return orderRepo.singleOrder(id)
    }

    override suspend fun getOrder(status:String): MutableList<OrderDataModel> {
        return orderRepo.getTeaBoyOrder(status)
    }

    override suspend fun acceptOrder(id: Int) {
        return orderRepo.acceptOrder(id)
    }

    override suspend fun rejectOrder(id: Int) {
        return orderRepo.rejectOrder(id)
    }

    override suspend fun deliveredOrder(id: Int) {
        return orderRepo.deliveredOrder(id)
    }

    override suspend fun singleOrderTeaBoy(id: Int): MutableList<OrderDataModel> {
        return orderRepo.singleOrderTeaBoy(id)
    }
}