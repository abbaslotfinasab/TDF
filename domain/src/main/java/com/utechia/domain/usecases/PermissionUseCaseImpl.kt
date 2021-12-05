package com.utechia.domain.usecases

import com.utechia.domain.model.PermissionModel
import com.utechia.domain.repository.PermissionRepo
import javax.inject.Inject

class PermissionUseCaseImpl @Inject constructor(private val permissionRepo: PermissionRepo):PermissionUseCase<PermissionModel> {

    override suspend fun execute(status: String): MutableList<PermissionModel> {
        return permissionRepo.getPermission(status)
    }

    override suspend fun get(id: Int): MutableList<PermissionModel> {
        return permissionRepo.get(id)
    }

    override suspend fun post(type: String, description:String,start:String,end:String):MutableList<PermissionModel> {
        return permissionRepo.post(type,description,start,end)
    }

    override suspend fun update(id: Int,status: String):MutableList<PermissionModel> {
        return permissionRepo.update(id,status)
    }
}