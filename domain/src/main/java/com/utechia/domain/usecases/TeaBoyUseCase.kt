package com.utechia.domain.usecases

import com.utechia.domain.model.RefreshmentModel

interface TeaBoyUseCase<R> {

    suspend fun getRefreshment(category:Int): MutableList<R>
    suspend fun order(refreshmentModel: RefreshmentModel)
    suspend fun like(refreshmentModel: RefreshmentModel)
    suspend fun delete(refreshmentModel: RefreshmentModel)
    suspend fun cancel(refreshmentModel: RefreshmentModel)
}