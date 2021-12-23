package com.utechia.domain.repository

import com.utechia.domain.model.OrderDataModel

interface TeaBoyOrderRepo {

    suspend fun acceptOrder(id:Int):MutableList<OrderDataModel>
    suspend fun rejectOrder(id:Int):MutableList<OrderDataModel>
    suspend fun deliveredOrder(id:Int)
    suspend fun getTeaBoyOrder(status:String):MutableList<OrderDataModel>
    suspend fun singleOrderTeaBoy(id:Int):MutableList<OrderDataModel>

}