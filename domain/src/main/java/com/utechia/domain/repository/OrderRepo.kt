package com.utechia.domain.repository

import com.utechia.domain.model.OrderDataModel

interface OrderRepo {

    suspend fun getOrder(status:String):MutableList<OrderDataModel>
    suspend fun cancelOrder(id:Int)
    suspend fun singleOrder(id:Int):MutableList<OrderDataModel>
    suspend fun acceptOrder(id:Int)
    suspend fun rejectOrder(id:Int)
    suspend fun deliveredOrder(id:Int)
    suspend fun getTeaBoyOrder(status:String):MutableList<OrderDataModel>
    suspend fun singleOrderTeaBoy(id:Int):MutableList<OrderDataModel>

}