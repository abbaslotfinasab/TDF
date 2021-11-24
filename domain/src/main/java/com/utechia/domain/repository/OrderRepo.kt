package com.utechia.domain.repository

import com.utechia.domain.model.OrderDataModel

interface OrderRepo {

    suspend fun getOrder(status:String):MutableList<OrderDataModel>
    suspend fun cancelOrder(id:Int)
}