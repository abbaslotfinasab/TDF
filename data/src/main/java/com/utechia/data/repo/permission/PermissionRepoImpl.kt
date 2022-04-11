package com.utechia.data.repo.permission

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.permission.PermissionModel
import com.utechia.domain.repository.permission.PermissionRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): PermissionRepo {

    override suspend fun getPermission(status: String): LiveData<PagingData<PermissionModel>> {
        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    PermissionPagingSource(service = service,status = status)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")
    }
}