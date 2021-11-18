package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.FavoriteModel
import com.utechia.domain.repository.FavoriteRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper

):FavoriteRepo {
    override suspend fun getFavorite(): MutableList<FavoriteModel> {
        if (networkHelper.isNetworkConnected()) {

            val result = service.getFavorite()

            when {

                result.isSuccessful && result.body()?.data != null ->
                    return result.body()?.data?.map { it.toDomain() }!!.toMutableList()

                result.body()?.data == null ->
                    throw IOException("no user")

                else ->
                    throw IOException("Server is Not Responding")

            }
        } else
            throw IOException("No Internet Connection")
    }

    override suspend fun like(id: Int) {

        if (networkHelper.isNetworkConnected()) {

            val result = service.like(id)

            /*  if (!result.isSuccessful){
                  throw IOException (result.errorBody().toString())
              }*/
        }


    }


    override suspend fun dislike(id: Int){

        if (networkHelper.isNetworkConnected()) {

            val result = service.dislike(id)

            /*  if (!result.isSuccessful){
                  throw IOException (result.errorBody().toString())
              }*/
        }
    }


}