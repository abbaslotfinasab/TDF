package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.dao.RoomDao
import com.utechia.data.mapper.RoomMapper
import com.utechia.data.utile.FakeData
import com.utechia.domain.moodel.RoomModel
import com.utechia.domain.repository.RoomRepo
import java.io.IOException
import javax.inject.Inject

class RoomRepoImpl @Inject constructor(

    private val fakeData: FakeData,
    private val roomMapper:dagger.Lazy<RoomMapper>,
    private val roomDao: RoomDao,
    private val service: Service,

    ):RoomRepo {
    @Throws(IOException::class)

    override suspend fun getRoom(day_id: Int,month_id:Int): MutableList<RoomModel> {

        fakeData.sendData()

        return roomDao.getAll(day_id,month_id).map {
            roomMapper.get().toMapper(it)
        }.toMutableList()

    }

}
