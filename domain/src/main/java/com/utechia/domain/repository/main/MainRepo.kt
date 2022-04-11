package com.utechia.domain.repository.main

import com.utechia.domain.model.main.NotificationCountModel

interface MainRepo {

    suspend fun getCountNotification(): NotificationCountModel
    suspend fun sendToken()
    suspend fun sendSteps(steps:Int,calory:Int,start:String,end:String)

}