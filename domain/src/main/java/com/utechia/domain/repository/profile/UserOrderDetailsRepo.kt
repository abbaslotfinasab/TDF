package com.utechia.domain.repository.profile

import com.utechia.domain.model.order.UserOrderDataModel

interface UserOrderDetailsRepo {

    suspend fun cancelOrder(id:Int): MutableList<UserOrderDataModel>
    suspend fun singleOrder(id:Int): MutableList<UserOrderDataModel>
    suspend fun rate(order:Int,rate:Int): MutableList<UserOrderDataModel>

}