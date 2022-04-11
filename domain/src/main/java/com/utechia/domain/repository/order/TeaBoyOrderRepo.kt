package com.utechia.domain.repository.order

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.order.TeaBoyOrderDataModel

interface TeaBoyOrderRepo {
    suspend fun getTeaBoyOrder(status:String): LiveData<PagingData<TeaBoyOrderDataModel>>
}