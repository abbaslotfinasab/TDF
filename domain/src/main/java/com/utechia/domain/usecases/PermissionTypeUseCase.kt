package com.utechia.domain.usecases

interface PermissionTypeUseCase<R> {

    suspend fun execute():MutableList<R>
}