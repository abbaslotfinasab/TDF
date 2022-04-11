package com.utechia.domain.usecases.notification


interface NotificationUseCase<R> {

    suspend fun getAll():R
}