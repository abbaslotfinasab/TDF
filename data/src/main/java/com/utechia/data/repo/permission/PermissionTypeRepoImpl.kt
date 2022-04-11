package com.utechia.data.repo.permission

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.repository.permission.PermissionTypeRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionTypeRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    ): PermissionTypeRepo {
    override suspend fun getType(): MutableList<String> {

        if (networkHelper.isNetworkConnected()){

            val result = service.getPermissionType()

            if (result.isSuccessful)
                return result.body()?.data!!
                else
                    throw IOException("Server is Not Responding")

        } else throw IOException("No Internet Connection")
    }
}