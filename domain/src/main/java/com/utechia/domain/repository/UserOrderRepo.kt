package com.utechia.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.UserOrderDataModel
import kotlinx.coroutines.flow.Flow

interface UserOrderRepo {

    suspend fun getOrder(status:String): LiveData<PagingData<UserOrderDataModel>>
}