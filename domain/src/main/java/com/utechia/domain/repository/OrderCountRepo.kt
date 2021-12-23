package com.utechia.domain.repository

import com.utechia.domain.model.OrderCountModel

interface OrderCountRepo {

    suspend fun getOrderCount():MutableList<OrderCountModel>

    suspend fun setStatus(status:Boolean):MutableList<OrderCountModel>

}