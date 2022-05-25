package com.utechia.data.repo.reservation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.utechia.data.api.Service
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.model.reservation.MeetingModel
import retrofit2.HttpException
import java.io.IOException

class MeetingPagingSource(
    private val service: Service,

    private val status:String

    ) : PagingSource<Int, MeetingModel>() {

    override fun getRefreshKey(state: PagingState<Int, MeetingModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MeetingModel> {
        return try {
            val pageIndex = params.key ?: PagingEnum.Number.page
            val response = service.getAllMeeting(page = pageIndex, page_size = PagingEnum.Size.page, status)

            LoadResult.Page(
                data = response.body()?.data?.list?.map { it.toDomain() }?: emptyList(),
                prevKey = if (pageIndex == PagingEnum.Number.page) null else pageIndex,
                nextKey =  if(pageIndex < (response.body()?.data?.totalPages ?: 0))
                    pageIndex.plus(1)
                else
                    null
            )
        }catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}