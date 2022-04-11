package com.utechia.domain.usecases.permission

import com.utechia.domain.repository.permission.PermissionTypeRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionTypeUseCaseImpl @Inject constructor(private val permissionTypeRepo: PermissionTypeRepo):
    PermissionTypeUseCase<String> {
    override suspend fun execute(): MutableList<String> {
        return permissionTypeRepo.getType()
    }
}