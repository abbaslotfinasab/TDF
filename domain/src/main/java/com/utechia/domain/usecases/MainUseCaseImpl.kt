package com.utechia.domain.usecases

import com.utechia.domain.model.NotificationCountModel
import com.utechia.domain.repository.MainRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainUseCaseImpl @Inject constructor(private val mainRepo: MainRepo)
    :MainUseCase {

    override suspend fun count(): NotificationCountModel {
        return mainRepo.getCountNotification()
    }

    override suspend fun sendToken() {
        return mainRepo.sendToken()
    }

    override suspend fun sendSteps(steps: Int, calory: Int, start: String, end: String) {
        mainRepo.sendSteps(steps,calory,start,end)
    }
}