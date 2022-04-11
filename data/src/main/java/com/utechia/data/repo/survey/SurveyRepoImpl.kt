package com.utechia.data.repo.survey

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.utechia.data.api.Service
import com.utechia.data.utile.NetworkHelper
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.survey.SurveyModel
import com.utechia.domain.repository.survey.SurveyRepo
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurveyRepoImpl @Inject constructor(
    private val service: Service,
    private val networkHelper: NetworkHelper,

    ): SurveyRepo {
    override suspend fun getSurveyList(status:String): LiveData<PagingData<SurveyModel>> {

        if (networkHelper.isNetworkConnected()) {

            return Pager(
                config = PagingConfig(
                    pageSize = PagingEnum.Size.page,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    SurveyPagingSource(service = service,status = status)
                }
            ).liveData

        } else
            throw IOException("No Internet Connection")

    }
}