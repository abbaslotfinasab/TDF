package com.utechia.data.repo.reservation

import com.utechia.data.api.Service
import com.utechia.data.dao.RoomDao
import com.utechia.data.entity.reservation.RoomData
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.reservation.RoomModel
import com.utechia.domain.repository.reservation.RoomRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val roomDao: RoomDao


): RoomRepo {

    override suspend fun getRoom(query:String?): MutableList<RoomModel> {

        if (networkHelper.isNetworkConnected()) {

            return if (query==null){
                val result = service.getReservationRoom()

                if (result.isSuccessful && result.body()?.data!=null){
                    roomDao.insertAll(result.body()?.data?: emptyList<RoomData>().toMutableList())
                    result.body()?.data?.map { it.toDomain() }!!.toMutableList()
                }else{
                    throw IOException("Server is Not Responding")
                }
            }else{
                roomDao.search(query).map {
                    it.toDomain()
                }.toMutableList()
            }

        } else throw IOException("No Internet Connection")
    }
}

