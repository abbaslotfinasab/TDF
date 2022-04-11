package com.utechia.domain.usecases.permission

interface PermissionUseCase<R> {

    suspend fun execute(status:String):R

}