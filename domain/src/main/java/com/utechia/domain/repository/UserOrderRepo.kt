package com.utechia.domain.repository

import com.utechia.domain.model.OrderDataModel

interface UserOrderRepo {

    suspend fun getOrder(status:String):MutableList<OrderDataModel>
    suspend fun cancelOrder(id:Int):MutableList<OrderDataModel>
    suspend fun singleOrder(id:Int):MutableList<OrderDataModel>

}