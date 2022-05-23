package com.utechia.data.repo.reservation

import com.utechia.data.api.Service
import com.utechia.data.dao.ProfileDao
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.repository.reservation.ReservationRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val profileDao: ProfileDao

    ): ReservationRepo {
    override suspend fun addGuess(id: Int) {
        profileDao.add(id,true)
    }

    override suspend fun removeGuess(id: Int) {
        profileDao.add(id,false)
    }

    override suspend fun deleteAll() {
        profileDao.deleteAll()
    }
}