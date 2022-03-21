package com.utechia.domain.repository

import com.utechia.domain.model.NotificationModel

interface NotificationRepo {

    suspend fun getAll():MutableList<NotificationModel>
    suspend fun delete(id:Int):MutableList<NotificationModel>
    suspend fun read(id:Int,readAll:Boolean?=false):MutableList<NotificationModel>

}