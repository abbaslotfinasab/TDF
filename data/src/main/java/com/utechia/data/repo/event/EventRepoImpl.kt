package com.utechia.data.repo.event


import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.event.EventModel
import com.utechia.domain.repository.event.EventRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): EventRepo {

    override suspend fun getEvent(status:String): LiveData<PagingData<EventModel>> {

        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    EventPagingSource(service = service,status = status)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")
    }
}
