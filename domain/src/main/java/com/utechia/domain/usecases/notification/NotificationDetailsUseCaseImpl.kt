package com.utechia.domain.usecases.notification

import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.repository.notification.NotificationDetailsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDetailsUseCaseImpl @Inject constructor(private val notificationRepo: NotificationDetailsRepo)
    : NotificationDetailsUseCase<NotificationModel> {


    override suspend fun delete(id: Int,deleteAll:Boolean?): MutableList<NotificationModel> {
        return notificationRepo.delete(id,deleteAll)
    }



    override suspend fun read(id: Int, readAll: Boolean?): MutableList<NotificationModel> {
        return notificationRepo.read(id,readAll)
    }

}