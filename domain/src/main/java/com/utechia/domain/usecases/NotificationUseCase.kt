package com.utechia.domain.usecases

import com.utechia.domain.model.NotificationModel

interface NotificationUseCase<R> {

    suspend fun getAll():MutableList<R>
    suspend fun delete(id:Int):MutableList<R>
    suspend fun read(id:Int):MutableList<R>

}