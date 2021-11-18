package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.entity.FavoriteBody
import com.utechia.data.entity.RefreshToken
import com.utechia.data.utile.NetworkHelper
import com.utechia.data.utile.SessionManager
import com.utechia.domain.model.FavoriteModel
import com.utechia.domain.repository.FavoriteRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val sessionManager: SessionManager,


    ):FavoriteRepo {
    override suspend fun getFavorite(): MutableList<FavoriteModel> {
        if (networkHelper.isNetworkConnected()) {

            var result = service.getFavorite()

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.getFavorite()
            }

            return when(result.code()){

                200 ->{
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                404 ->{
                    emptyList<FavoriteModel>().toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else
            throw IOException("No Internet Connection")
    }

    override suspend fun getExist(title: String): MutableList<FavoriteModel> {

        if (networkHelper.isNetworkConnected()) {

            var result = service.exist(title)

            if (result.code() == 401) {

                sessionManager.updateAuthToken(
                    service.refresh(
                        RefreshToken(
                            sessionManager.fetchHomeId()
                                .toString()
                        )
                    ).body()?.data.toString()
                )
                result = service.exist(title)
            }

            return when (result.code()) {

                200 -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                404 ->
                    return emptyList<FavoriteModel>().toMutableList()

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun like(id: Int){

        var result = service.like(FavoriteBody(id))

        if (result.code() == 401) {

            sessionManager.updateAuthToken(
                service.refresh(
                    RefreshToken(
                        sessionManager.fetchHomeId()
                            .toString()
                    )
                ).body()?.data.toString()
            )
            result = service.like(FavoriteBody(id))
        }

        if (result.code()==400)
            throw IOException("This item already exist ")
    }

    override suspend fun dislike(id: Int) = service.dislike(id)

}