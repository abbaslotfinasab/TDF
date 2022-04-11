package com.utechia.data.repo.ticket

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.utechia.data.api.Service
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.enum.TicketEnum
import com.utechia.domain.model.ticket.TicketModel
import retrofit2.HttpException
import java.io.IOException

class TicketPagingSource(
    private val service: Service,
    private var status: String?=TicketEnum.Open.ticket


) : PagingSource<Int, TicketModel>() {

    override fun getRefreshKey(state: PagingState<Int, TicketModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TicketModel> {
        return try {
            val pageIndex = params.key ?: PagingEnum.Number.page
            val response = status?.let { service.getTicketList(status= it,page = pageIndex, page_size = PagingEnum.Size.page) }

            LoadResult.Page(
                data = response?.body()?.data?.list?.map { it.toDomain() }?: emptyList(),
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