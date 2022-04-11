package com.utechia.domain.repository.notification

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.notification.NotificationModel

interface NotificationRepo {

    suspend fun getAll(): LiveData<PagingData<NotificationModel>>


}