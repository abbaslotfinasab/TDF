package com.utechia.domain.usecases.event


interface EventUseCase <R>{

    suspend fun execute(status:String):R

}