package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.dao.RefreshmentDao
import com.utechia.data.entity.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.FavoriteModel
import com.utechia.domain.repository.FavoriteRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val refreshmentDao: RefreshmentDao,


    ):FavoriteRepo {
    override suspend fun getFavorite(): MutableList<FavoriteModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getFavorite()


            return when(result.isSuccessful){

                 true->{
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else
            throw IOException("No Internet Connection")
    }

    override suspend fun getExist(title: String): MutableList<FavoriteModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.exist(title)

            return when (result.isSuccessful) {

                true -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    override suspend fun like(id: Int) {
        refreshmentDao.like(id)
        service.like(FavoriteBody(id))
    }

    override suspend fun dislike(id: Int){
        refreshmentDao.dislike(id)
        service.dislike(id)
    }

}