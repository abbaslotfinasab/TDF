package com.utechia.data.repo.permission

import com.utechia.data.api.Service
import com.utechia.data.entity.permission.PermissionPostBody
import com.utechia.data.entity.permission.PermissionUpdateBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.permission.PermissionModel
import com.utechia.domain.repository.permission.PermissionDetailsRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionDetailsRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): PermissionDetailsRepo {

    override suspend fun get(id: Int): MutableList<PermissionModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getSinglePermission(id)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")


    }

    override suspend fun post(
        type: String,
        description: String,
        start: String,
        end: String
    ): MutableList<PermissionModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.postPermission(PermissionPostBody(type, description, start, end))

            return when(result.code()){

                200 ->{
                    emptyList<PermissionModel>().toMutableList()
                }

                201 ->{
                    emptyList<PermissionModel>().toMutableList()
                }

                406 -> {
                    throw IOException("Permission already exist")
                }

                else -> throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun update(id: Int, status: String):MutableList<PermissionModel> {
        if (networkHelper.isNetworkConnected()) {

            val result = service.updatePermission(PermissionUpdateBody(id,status))

            return when (result.isSuccessful) {

                true -> {
                    emptyList<PermissionModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}