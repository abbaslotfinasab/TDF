package com.utechia.domain.usecases.notification


interface NotificationDetailsUseCase<R> {

    suspend fun delete(id:Int,deleteAll:Boolean?=false):MutableList<R>
    suspend fun read(id:Int,readAll:Boolean?=false):MutableList<R>

}