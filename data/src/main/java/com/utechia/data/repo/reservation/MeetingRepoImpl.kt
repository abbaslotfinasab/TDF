package com.utechia.data.repo.reservation

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.repo.notification.NotificationPagingSource
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.reservation.MeetingModel
import com.utechia.domain.repository.reservation.MeetingRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MeetingRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): MeetingRepo {
    override suspend fun getAllMeeting(status:String): LiveData<PagingData<MeetingModel>> {

        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    MeetingPagingSource(service = service,status)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")

    }
}