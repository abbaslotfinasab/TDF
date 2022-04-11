package com.utechia.domain.repository.permission

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.permission.PermissionModel


interface PermissionRepo {

    suspend fun getPermission(status:String): LiveData<PagingData<PermissionModel>>

}