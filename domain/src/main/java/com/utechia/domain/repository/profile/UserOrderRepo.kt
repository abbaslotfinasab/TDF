package com.utechia.domain.repository.profile

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.order.UserOrderDataModel

interface UserOrderRepo {

    suspend fun getOrder(status:String): LiveData<PagingData<UserOrderDataModel>>
}