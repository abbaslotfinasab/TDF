package com.utechia.domain.repository.order

import com.utechia.domain.model.order.TeaBoyOrderDataModel

interface TeaBoyOrderDetailsRepo {

    suspend fun acceptOrder(id:Int):MutableList<TeaBoyOrderDataModel>
    suspend fun rejectOrder(id:Int):MutableList<TeaBoyOrderDataModel>
    suspend fun deliveredOrder(id:Int):MutableList<TeaBoyOrderDataModel>
    suspend fun singleOrderTeaBoy(id:Int):MutableList<TeaBoyOrderDataModel>
}