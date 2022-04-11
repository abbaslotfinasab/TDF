package com.utechia.domain.usecases.permission

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.permission.PermissionModel
import com.utechia.domain.repository.permission.PermissionRepo
import javax.inject.Inject

class PermissionUseCaseImpl @Inject constructor(private val permissionRepo: PermissionRepo):
    PermissionUseCase<LiveData<PagingData<PermissionModel>>> {

    override suspend fun execute(status: String): LiveData<PagingData<PermissionModel>>{
        return permissionRepo.getPermission(status)
    }
}