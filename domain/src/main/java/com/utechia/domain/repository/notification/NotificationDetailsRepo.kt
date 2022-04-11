package com.utechia.domain.repository.notification

import com.utechia.domain.model.notification.NotificationModel

interface NotificationDetailsRepo {

    suspend fun delete(id:Int,deleteAll:Boolean?=false):MutableList<NotificationModel>
    suspend fun read(id:Int,readAll:Boolean?=false):MutableList<NotificationModel>

}