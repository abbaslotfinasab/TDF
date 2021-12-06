package com.utechia.data.repo

import android.util.Log
import com.utechia.data.api.Service
import com.utechia.data.entity.PermissionPostBody
import com.utechia.data.entity.PermissionUpdateBody
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.PermissionModel
import com.utechia.domain.repository.PermissionRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PermissionRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,
):PermissionRepo {

    override suspend fun getPermission(status: String): MutableList<PermissionModel> {

        if (networkHelper.isNetworkConnected()) {

            var result = service.getPermission(status)

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.getPermission(status)
            }
            return when (result.code()) {

                200 -> {
                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun get(id: Int): MutableList<PermissionModel> {


        if (networkHelper.isNetworkConnected()) {

            var result = service.getSinglePermission(id)

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.getSinglePermission(id)
            }
            return when (result.code()) {

                200 -> {
                    Log.d("aslan", result.body()?.data?.get(0)?.type!!)
                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
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

            if (result.isSuccessful) {

                return result.body()?.data!!.map { it.toDomain() }.toMutableList()

            } else
                throw IOException("Server is Not Responding")

        } else throw IOException("No Internet Connection")

    }

    override suspend fun update(id: Int, status: String):MutableList<PermissionModel> {
        if (networkHelper.isNetworkConnected()) {

            var result = service.updatePermission(PermissionUpdateBody(id,status))

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.updatePermission(PermissionUpdateBody(id,status))
            }
            return when (result.code()) {

                200 -> {
                    result.body()?.data!!.map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }
}