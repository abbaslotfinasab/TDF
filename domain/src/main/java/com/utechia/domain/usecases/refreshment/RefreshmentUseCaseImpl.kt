package com.utechia.domain.usecases.refreshment

import com.utechia.domain.model.refreshment.RefreshmentModel
import com.utechia.domain.repository.refreshment.RefreshmentRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshmentUseCaseImpl @Inject constructor(private val refreshmentRepo: RefreshmentRepo):
    RefreshmentUseCase<RefreshmentModel> {
    override suspend fun get(type: String): MutableList<RefreshmentModel> {
        return refreshmentRepo.getRefreshment(type)
    }

    override suspend fun search(search: String, type: String): MutableList<RefreshmentModel> {
        return refreshmentRepo.search(search, type)

    }

    override suspend fun cart(): MutableList<RefreshmentModel> {
        return refreshmentRepo.getCart()
    }
    override suspend fun postCart(id: Int, quantity: Int) {
        return refreshmentRepo.postCart(id,quantity)
    }

    override suspend fun updateCart(id: Int, quantity: Int) {
        return refreshmentRepo.updateCart(id,quantity)
    }

    override suspend fun deleteCart(id: Int){
        return refreshmentRepo.deleteCart(id)
    }

    override suspend fun like(id: Int) {
        return refreshmentRepo.like(id)
    }

    override suspend fun dislike(id: Int) {
        return refreshmentRepo.dislike(id)
    }

}