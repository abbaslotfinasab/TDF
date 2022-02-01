package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.dao.RefreshmentDao
import com.utechia.data.entity.CartBody
import com.utechia.data.entity.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.repository.RefreshmentRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshmentRepoImpl @Inject constructor(

    private val service:Service,
    private val networkHelper: NetworkHelper,
    private val refreshmentDao: RefreshmentDao,

    ):RefreshmentRepo {

    @Throws(IOException::class)
    override suspend fun getRefreshment(type: String): MutableList<RefreshmentModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getRefreshment(type)

            return when (result.isSuccessful && result.body() != null) {

                true -> {

                    if (refreshmentDao.getALL(type).size==0)
                    result.body()?.data?.let { refreshmentDao.insertAll(it) }

                    refreshmentDao.getALL(type).map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }

    @Throws(IOException::class)
    override suspend fun search(search: String, type: String): MutableList<RefreshmentModel> {

        if (networkHelper.isNetworkConnected()) {

            return refreshmentDao.search(search).map {
                it.toDomain()
            }.toMutableList()

        } else throw IOException("No Internet Connection")
    }

    @Throws(IOException::class)
    override suspend fun getCart(id: Int): MutableList<RefreshmentModel> {
        if (networkHelper.isNetworkConnected()) {

            val result = service.getCart(id)

            return when (result.isSuccessful && result.body() != null) {

                true -> {
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun postCart(id: Int, quantity: Int) {
        refreshmentDao.updateNumber(id,quantity)
        service.postCart(CartBody(id,quantity))
    }

    override suspend fun updateCart(id: Int, quantity: Int){

        refreshmentDao.updateNumber(id, quantity)
        service.updateCart(CartBody(id, quantity))
    }

    override suspend fun deleteCart(id: Int) {

        refreshmentDao.deleteItem(id)
        service.deleteRefreshment(id)
    }

    override suspend fun like(id: Int) = service.like(FavoriteBody(id))

    override suspend fun dislike(id: Int) = service.dislike(id)
}
