package com.utechia.data.repo.refreshment

import com.utechia.data.api.Service
import com.utechia.data.dao.RefreshmentDao
import com.utechia.data.entity.cart.CartBody
import com.utechia.data.entity.favorite.FavoriteBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.refreshment.RefreshmentModel
import com.utechia.domain.repository.refreshment.RefreshmentRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshmentRepoImpl @Inject constructor(

    private val service:Service,
    private val networkHelper: NetworkHelper,
    private val refreshmentDao: RefreshmentDao,

    ): RefreshmentRepo {

    @Throws(IOException::class)
    override suspend fun getRefreshment(type: String): MutableList<RefreshmentModel> {

        if (networkHelper.isNetworkConnected()) {

            if (type=="") {

                val result = service.getRefreshment()

                return when (result.isSuccessful && result.body() != null) {

                    true -> {

                        result.body()?.data?.let { refreshmentDao.insertAll(it) }

                        refreshmentDao.getALL(type).map { it.toDomain() }.toMutableList()
                    }

                    else ->
                        throw IOException("Server is Not Responding")

                }
            }
            else {
                if(refreshmentDao.getSize().size!=0) {
                    return refreshmentDao.getALL(type).map { it.toDomain() }.toMutableList()
                }
                else{

                    val result = service.getRefreshment()

                    return when (result.isSuccessful && result.body() != null) {

                        true -> {

                            result.body()?.data?.let { refreshmentDao.insertAll(it) }

                            refreshmentDao.getALL(type).map { it.toDomain() }.toMutableList()
                        }

                        else ->
                            throw IOException("Server is Not Responding")

                    }
                }
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
    override suspend fun getCart(): MutableList<RefreshmentModel> {
        if (networkHelper.isNetworkConnected()) {

            val result = service.getCart()

            return when (result.isSuccessful) {

                true -> {

                    result.body()?.data?.map {
                        it.items?.map { it1 ->
                            it1.quantity?.let { it2 ->
                                it1.food.id?.let { it3 ->
                                    refreshmentDao.updateNumber(
                                        it3,
                                        it2
                                    )
                                }
                            }
                        }
                    }
                    emptyList<RefreshmentModel>().toMutableList()
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

    override suspend fun like(id: Int) {
        refreshmentDao.like(id)
        service.like(FavoriteBody(id))
    }

    override suspend fun dislike(id: Int){
        refreshmentDao.dislike(id)
        service.dislike(id)
    }
}
