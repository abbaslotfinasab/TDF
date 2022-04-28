package com.utechia.data.repo.cart

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.cart.OfficeModel
import com.utechia.domain.repository.cart.OfficeRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,



    ): OfficeRepo {
    override suspend fun getAllOffice(): MutableList<OfficeModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getOffice()

            return when (result.isSuccessful && result.body() !=null) {

                true -> {
                    result.body()?.data?.myRoom.let {myRoom ->
                        myRoom?.floor?.let { it ->
                            it.id?.let { it1 ->
                                sessionManager.saveMyRoom(myRoom.workStation.toString(),myRoom.location.toString(),
                                    it1
                                )
                            }
                        }
                    }
                    result.body()?.data?.list?.map { it.toDomain() }!!.toMutableList()
                }
                else ->

                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

}