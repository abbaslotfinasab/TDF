package com.utechia.data.repo.order.userorder

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.order.UserOrderDataModel
import com.utechia.domain.repository.profile.UserOrderRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserOrderRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,
): UserOrderRepo {
    override suspend fun getOrder(status: String): LiveData<PagingData<UserOrderDataModel>> {

        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    UserOrderPagingSource(service = service,status = status)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")
    }
}