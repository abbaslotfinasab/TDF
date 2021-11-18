package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.repository.RefreshmentRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshmentRepoImpl @Inject constructor(

    private val service:Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,

    ):RefreshmentRepo {

    @Throws(IOException::class)
    override suspend fun getRefreshment(type: String): MutableList<RefreshmentModel> {

        if (networkHelper.isNetworkConnected()) {

            var result = service.getRefreshment(type)

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).toString()
                )
                result = service.getRefreshment(type)
            }

                return when (result.code()) {

                    200 -> {
                        result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                    }

                    else ->
                        throw IOException("Server is Not Responding")
                }


            } else throw IOException("No Internet Connection")
        }

        @Throws(IOException::class)
        override suspend fun search(search: String, type: String): MutableList<RefreshmentModel> {

            if (networkHelper.isNetworkConnected()) {

                var result = service.search(search, type)

                if (result.code() == 401) {

                    sessionManager.updateAuthToken(
                        service.refresh(
                            RefreshToken(
                                sessionManager.fetchHomeId()
                                    .toString()
                            )
                        ).toString()
                    )
                    result = service.search(search, type)

                }
                return when (result.code()) {

                    200 -> {
                        result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                    }

                    404 -> {
                        emptyList<RefreshmentModel>().toMutableList()
                    }

                    else ->
                        throw IOException("Server is Not Responding")
                }

            } else throw IOException("No Internet Connection")
        }

    @Throws(IOException::class)
    override suspend fun getCart(id: Int): MutableList<RefreshmentModel> {
        if (networkHelper.isNetworkConnected()) {

            var result = service.getCart(id)

            if (result.code()==401){

                sessionManager.updateAuthToken(
                    service.refresh(RefreshToken(sessionManager.fetchHomeId()
                        .toString())).toString()
                )
                result = service.getCart(id)

            }

            return when(result.code()){

                200 ->{
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                404 ->{
                    emptyList<RefreshmentModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }


        } else throw IOException("No Internet Connection")

    }
}
