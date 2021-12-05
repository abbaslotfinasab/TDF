package com.utechia.domain.usecases

interface PermissionUseCase<R> {

    suspend fun execute(status:String):MutableList<R>
    suspend fun get(id:Int):MutableList<R>
    suspend fun post(type: String, description:String,start:String,end:String):MutableList<R>
    suspend fun update(id:Int,status:String):MutableList<R>


}