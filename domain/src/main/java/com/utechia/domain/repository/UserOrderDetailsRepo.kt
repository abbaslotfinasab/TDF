package com.utechia.domain.repository

import com.utechia.domain.model.UserOrderDataModel

interface UserOrderDetailsRepo {

    suspend fun cancelOrder(id:Int): MutableList<UserOrderDataModel>
    suspend fun singleOrder(id:Int): MutableList<UserOrderDataModel>
    suspend fun rate(order:Int,rate:Int): MutableList<UserOrderDataModel>

}