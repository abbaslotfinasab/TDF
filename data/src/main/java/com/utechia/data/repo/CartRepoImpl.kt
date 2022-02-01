package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.dao.RefreshmentDao
import com.utechia.data.entity.CartBody
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.CartModel
import com.utechia.domain.repository.CartRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val refreshmentDao: RefreshmentDao,


    ):CartRepo {
    override suspend fun getCart(): MutableList<CartModel> {

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
                result.body()?.data?.map { it.toDomain()}!!.toMutableList()
                }
                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")
    }


    override suspend fun postCart(id: Int, quantity: Int){
        refreshmentDao.updateNumber(id,quantity)
        service.postCart(CartBody(id,quantity))
    }

    override suspend fun updateCart(id: Int, quantity: Int) {
        refreshmentDao.updateNumber(id,quantity)
        service.updateCart(CartBody(id, quantity))
    }

    override suspend fun deleteCart(id: Int):MutableList<CartModel>{

        if (networkHelper.isNetworkConnected()) {

            val result = service.deleteCart(id)

            return when (result.isSuccessful) {

                true -> {
                    refreshmentDao.deleteItem(id)

                    emptyList<CartModel>().toMutableList()
                }
                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }

    override suspend fun checkout(): MutableList<CartModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.postOrder()

            return when (result.isSuccessful) {

                true -> {
                    refreshmentDao.deleteAll()

                    emptyList<CartModel>().toMutableList()
                }
                else -> {
                    throw IOException("Server is Not Responding")
                }
            }
        } else throw IOException("No Internet Connection")
    }
}