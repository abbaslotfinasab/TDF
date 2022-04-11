package com.utechia.data.repo.notification

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.repository.notification.NotificationRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    ): NotificationRepo {
    override suspend fun getAll(): LiveData<PagingData<NotificationModel>> {

        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    NotificationPagingSource(service = service)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")

    }
}