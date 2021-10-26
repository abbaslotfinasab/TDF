package com.utechia.domain.usecases

import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.repository.TeaBoyRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeaBoyUseCaseImpl @Inject constructor(private val teaBoyRepo: TeaBoyRepo)
    :TeaBoyUseCase<RefreshmentModel>
{
    override suspend fun getRefreshment(category:Int): MutableList<RefreshmentModel> {
        return teaBoyRepo.getRefreshment(category)
    }

    override suspend fun order(refreshmentModel: MutableList<RefreshmentModel>) {
        return teaBoyRepo.order(refreshmentModel)
    }

    override suspend fun like(refreshmentModel: RefreshmentModel) {
       return teaBoyRepo.like(refreshmentModel)
    }

    override suspend fun delete(id:Int) {
        return teaBoyRepo.delete(id)

    }

    override suspend fun cancel(refreshmentModel: RefreshmentModel) {
        return teaBoyRepo.cancel(refreshmentModel)
    }
}