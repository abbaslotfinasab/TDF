package com.utechia.data.repo.survey

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.utechia.data.api.Service
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.enum.SurveyEnum
import com.utechia.domain.model.survey.SurveyModel
import retrofit2.HttpException
import java.io.IOException

class SurveyPagingSource(
    private val service: Service,
    private var status: String?= SurveyEnum.Evaluate.survey



) : PagingSource<Int, SurveyModel>() {

    override fun getRefreshKey(state: PagingState<Int, SurveyModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyModel> {
        return try {
            val pageIndex = params.key ?: PagingEnum.Number.page
            val response =
                if (status == SurveyEnum.Evaluate.survey)
                service.getSurveyList(page = pageIndex, page_size = PagingEnum.Size.page)
            else
                    service.getEvaluate(page = pageIndex, page_size = PagingEnum.Size.page)


            LoadResult.Page(
                data = response.body()?.data?.list?.map { it.toDomain() }?: emptyList(),
                prevKey = if (pageIndex == PagingEnum.Number.page) null else pageIndex,
                nextKey = null
            )
        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}