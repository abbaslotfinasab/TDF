package com.utechia.data.repo

import com.utechia.data.api.Service
import com.utechia.data.dao.DayDao
import com.utechia.data.mapper.DayMapper
import com.utechia.domain.moodel.DayModel
import com.utechia.domain.repository.DayRepo
import java.io.IOException
import javax.inject.Inject

class DayRepoImpl @Inject constructor(

    private val dayMapper:dagger.Lazy<DayMapper>,
    private val dayDao: DayDao,
    private val service: Service,

    ): DayRepo {
    @Throws(IOException::class)

    override suspend fun getDay(month_id: Int): MutableList<DayModel> {
        return dayDao.getAll(month_id).map {
            dayMapper.get().toMapper(it)
        }.toMutableList()

    }

}
