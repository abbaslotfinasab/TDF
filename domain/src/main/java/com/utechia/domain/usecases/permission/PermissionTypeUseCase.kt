package com.utechia.domain.usecases.permission

interface PermissionTypeUseCase<R> {

    suspend fun execute():MutableList<R>
}