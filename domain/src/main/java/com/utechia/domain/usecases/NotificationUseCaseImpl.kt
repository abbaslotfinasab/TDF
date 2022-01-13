package com.utechia.domain.usecases

import com.utechia.domain.model.NotificationModel
import com.utechia.domain.repository.NotificationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationUseCaseImpl @Inject constructor(private val notificationRepo: NotificationRepo)
    :NotificationUseCase<NotificationModel>{
    override suspend fun getAll(): MutableList<NotificationModel> {
        return notificationRepo.getAll()
    }

    override suspend fun delete(id: Int): MutableList<NotificationModel> {
        return notificationRepo.delete(id)
    }

    override suspend fun read(id: Int): MutableList<NotificationModel> {
        return notificationRepo.read(id)
    }

}