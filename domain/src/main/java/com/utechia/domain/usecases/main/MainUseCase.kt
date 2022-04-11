package com.utechia.domain.usecases.main

import com.utechia.domain.model.main.NotificationCountModel

interface MainUseCase {

    suspend fun count(): NotificationCountModel
    suspend fun sendToken()
    suspend fun sendSteps(steps:Int,calory:Int,start:String,end:String)

}