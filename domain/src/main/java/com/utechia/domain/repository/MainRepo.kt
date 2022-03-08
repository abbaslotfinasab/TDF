package com.utechia.domain.repository

import com.utechia.domain.model.NotificationCountModel

interface MainRepo {

    suspend fun getCountNotification():NotificationCountModel
    suspend fun sendToken()
    suspend fun sendSteps(steps:Int,calory:Int,start:String,end:String)

}