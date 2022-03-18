package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.repository.OfficeRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,


    ): OfficeRepo {
    override suspend fun getAllOffice(): MutableList<String> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getOffice()

            return when (result.isSuccessful && result.body() !=null) {

                true -> {

                    result.body()?.data!!.toMutableList()
                }
                else ->

                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun getOffice(office: String): MutableList<String> {
        return emptyList<String>().toMutableList()
    }
}