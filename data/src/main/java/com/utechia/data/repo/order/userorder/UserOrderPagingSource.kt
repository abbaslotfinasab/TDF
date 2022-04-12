package com.utechia.data.repo.order.userorder

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.utechia.data.api.Service
import com.utechia.domain.enum.OrderEnum
import com.utechia.domain.enum.PagingEnum
import com.utechia.domain.model.order.UserOrderDataModel
import retrofit2.HttpException
import java.io.IOException

class UserOrderPagingSource(
    private val service: Service,
    private var status: String?=OrderEnum.Pending.order


    ) : PagingSource<Int, UserOrderDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, UserOrderDataModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserOrderDataModel> {
        return try {
            val pageIndex = params.key ?: PagingEnum.Number.page
            val response = status?.let { service.getOrder(status= it,page = pageIndex, page_size = PagingEnum.Size.page) }

            LoadResult.Page(
                data = response?.body()?.data?.list?.map { it.toDomain() }?: emptyList(),
                prevKey = if (pageIndex == PagingEnum.Number.page) null else pageIndex,
                nextKey =
                if(pageIndex<response?.body()?.data?.totalPages?:0)
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