package com.utechia.domain.repository.order

import com.utechia.domain.model.order.OrderCountModel

interface OrderCountRepo {

    suspend fun getOrderCount():MutableList<OrderCountModel>

    suspend fun setStatus(status:Boolean):MutableList<OrderCountModel>

}