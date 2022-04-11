package com.utechia.data.repo.order.teaboyorder

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.utechia.data.api.Service
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import retrofit2.HttpException
import java.io.IOException

class TeaBoyOrderPagingSource(
    private val service: Service,
    private var status: String?=OrderEnum.Pending.order


) : PagingSource<Int, TeaBoyOrderDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, TeaBoyOrderDataModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TeaBoyOrderDataModel> {
        return try {
            val pageIndex = params.key ?: PagingEnum.Number.page
            val response = status?.let { service.getTeaBoyOrder(status= it,page = pageIndex, page_size = PagingEnum.Size.page) }

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