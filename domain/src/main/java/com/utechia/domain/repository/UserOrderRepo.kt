package com.utechia.domain.repository

import com.utechia.domain.model.UserOrderDataModel

interface UserOrderRepo {

    suspend fun getOrder(status:String):MutableList<UserOrderDataModel>
    suspend fun cancelOrder(id:Int):MutableList<UserOrderDataModel>
    suspend fun singleOrder(id:Int):MutableList<UserOrderDataModel>

}