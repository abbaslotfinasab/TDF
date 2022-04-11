package com.utechia.domain.usecases.permission

import com.utechia.domain.model.permission.PermissionModel
import com.utechia.domain.repository.permission.PermissionDetailsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionDetailsUseCaseImpl @Inject constructor(private val permissionRepo: PermissionDetailsRepo):
    PermissionDetailsUseCase<PermissionModel> {

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