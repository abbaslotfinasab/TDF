package com.utechia.domain.usecases

import com.utechia.domain.repository.PermissionTypeRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionTypeUseCaseImpl @Inject constructor(private val permissionTypeRepo: PermissionTypeRepo):
    PermissionTypeUseCase<String> {
    override suspend fun execute(): MutableList<String> {
        return permissionTypeRepo.getType()
    }
}