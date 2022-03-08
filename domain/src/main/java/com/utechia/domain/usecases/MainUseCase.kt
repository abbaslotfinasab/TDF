package com.utechia.domain.usecases

import com.utechia.domain.model.NotificationCountModel

interface MainUseCase {

    suspend fun count(): NotificationCountModel
    suspend fun sendToken()
    suspend fun sendSteps(steps:Int,calory:Int,start:String,end:String)

}