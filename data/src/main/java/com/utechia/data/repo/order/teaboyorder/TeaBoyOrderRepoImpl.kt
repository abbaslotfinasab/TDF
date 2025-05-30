package com.utechia.data.repo.order.teaboyorder

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.domain.repository.order.TeaBoyOrderRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyOrderRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
    ): TeaBoyOrderRepo {

    override suspend fun getTeaBoyOrder(status:String): LiveData<PagingData<TeaBoyOrderDataModel>> {


        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    TeaBoyOrderPagingSource(service = service,status = status)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")
    }
}