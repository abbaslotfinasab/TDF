package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.repository.RefreshmentRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshmentRepoImpl @Inject constructor(

    private val service:Service,
    private val networkHelper: NetworkHelper
):RefreshmentRepo {

    @Throws(IOException::class)
    override suspend fun getRefreshment(type: String): MutableList<RefreshmentModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getRefreshment(type)

            when {

                result.isSuccessful && result.body()?.data != null ->
                    return result.body()!!.data?.map { it.toDomain() }!!.toMutableList()

                result.body()?.data == null ->
                    throw IOException(result.errorBody().toString())


                else ->
                    throw IOException("Server is Not Responding")

            }

        } else throw IOException("No Internet Connection")

    }

    @Throws(IOException::class)
    override suspend fun search(search: String, type: String): MutableList<RefreshmentModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.search(search, type)

            when {

                result.isSuccessful && result.body()?.data != null ->
                    return result.body()!!.data?.map { it.toDomain() }!!.toMutableList()

                result.body()?.data == null ->
                    throw IOException(result.errorBody().toString())


                else ->
                    throw IOException("Server is Not Responding")
            }
        } else throw IOException("No Internet Connection")
    }

    @Throws(IOException::class)
    override suspend fun getCart(id: Int): MutableList<RefreshmentModel> {
        if (networkHelper.isNetworkConnected()) {

            val result = service.getCart(id)

            when {

                result.isSuccessful && result.body()?.data != null ->
                    return result.body()!!.data?.map { it.toDomain() }!!.toMutableList()

                result.body()?.data == null ->
                    throw IOException(result.errorBody().toString())

                else ->
                    throw IOException("Server is Not Responding")
            }
        } else throw IOException("No Internet Connection")

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
