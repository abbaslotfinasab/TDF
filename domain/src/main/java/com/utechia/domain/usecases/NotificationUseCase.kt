package com.utechia.domain.usecases


interface NotificationUseCase<R> {

    suspend fun getAll():MutableList<R>
    suspend fun delete(id:Int,deleteAll:Boolean?=false):MutableList<R>
    suspend fun read(id:Int,readAll:Boolean?=false):MutableList<R>

}