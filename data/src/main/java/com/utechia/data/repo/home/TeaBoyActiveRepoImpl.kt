package com.utechia.data.repo.home

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.home.TeaBoyActiveModel
import com.utechia.domain.repository.home.TeaBoyActiveRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyActiveRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

): TeaBoyActiveRepo {

    override suspend fun getActiveList(): MutableList<TeaBoyActiveModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getTeaBoyActive()

            return when (result.isSuccessful && result.body() != null) {

                true -> {
                    result.body()?.data?.list?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

}