package com.utechia.domain.usecases.home

import com.utechia.domain.model.refreshment.RefreshmentModel

interface TeaBoyUseCase<R> {

    suspend fun getRefreshment(category:Int): MutableList<R>
    suspend fun getCard(id:Int): MutableList<R>
    suspend fun order(refreshmentModel: MutableList<RefreshmentModel>)
    suspend fun like(refreshmentModel: RefreshmentModel)
    suspend fun delete(id:Int)
    suspend fun cancel(refreshmentModel: RefreshmentModel)
}