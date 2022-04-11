package com.utechia.data.repo.profile

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.domain.repository.profile.ProfileRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper

): ProfileRepo {

    override suspend fun getProfile(): ProfileModel {
        if (networkHelper.isNetworkConnected()) {

            val result = service.getProfile()

            return when (result.isSuccessful && result.body() != null) {

                true -> {
                    result.body()?.data?.toDomain()!!
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }
}