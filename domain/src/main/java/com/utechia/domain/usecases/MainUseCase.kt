package com.utechia.domain.usecases

import com.utechia.domain.model.NotificationCountModel

interface MainUseCase {

    suspend fun count(): NotificationCountModel

}