package com.utechia.domain.usecases.permission

interface PermissionDetailsUseCase<R> {

    suspend fun get(id:Int):MutableList<R>
    suspend fun post(type: String, description:String,start:String,end:String):MutableList<R>
    suspend fun update(id:Int,status:String):MutableList<R>
}