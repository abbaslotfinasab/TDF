package com.utechia.domain.usecases.notification

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.repository.notification.NotificationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationUseCaseImpl @Inject constructor(private val notificationRepo: NotificationRepo)
    : NotificationUseCase<LiveData<PagingData<NotificationModel>>> {

    override suspend fun getAll(): LiveData<PagingData<NotificationModel>> {
        return notificationRepo.getAll()
    }

}