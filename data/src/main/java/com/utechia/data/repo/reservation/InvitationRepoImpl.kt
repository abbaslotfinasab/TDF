package com.utechia.data.repo.reservation

import com.utechia.data.api.Service
import com.utechia.data.dao.ProfileDao
import com.utechia.data.entity.profile.ProfileData
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.domain.repository.reservation.InvitationRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvitationRepoImpl @Inject constructor(

    private val service: Service,
    private val networkHelper: NetworkHelper,
    private val profileDao: ProfileDao


): InvitationRepo {

    override suspend fun getUserList(query:String): MutableList<ProfileModel> {

        if (networkHelper.isNetworkConnected()) {

            val result = service.getUserList(query)

            when (result.isSuccessful) {

                true -> {
                    profileDao.insertAll(result.body()?.data?: emptyList<ProfileData>().toMutableList())
                    return profileDao.search(query).map { it.toDomain() }.toMutableList()
                }

                else ->
                    throw IOException("Server is Not Responding")
            }

        } else throw IOException("No Internet Connection")

    }
}