package com.utechia.domain.usecases

import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.repository.RefreshmentRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshmentUseCaseImpl @Inject constructor(private val refreshmentRepo: RefreshmentRepo):RefreshmentUseCase<RefreshmentModel> {
    override suspend fun get(type: String): MutableList<RefreshmentModel> {
        return refreshmentRepo.getRefreshment(type)
    }

    override suspend fun search(search: String): MutableList<RefreshmentModel> {
        return refreshmentRepo.search(search)

    }

    override suspend fun cart(id: Int): MutableList<RefreshmentModel> {
        return refreshmentRepo.getCart(id)
    }
}