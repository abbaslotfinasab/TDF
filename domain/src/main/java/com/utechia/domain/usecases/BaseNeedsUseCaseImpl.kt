package com.utechia.domain.usecases

import com.utechia.domain.model.BaseNeedsModel
import com.utechia.domain.repository.BaseNeedsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseNeedsUseCaseImpl @Inject constructor(private val baseNeedRepo: BaseNeedsRepo)
    :BaseNeedsUseCase{
    override suspend fun execute(): BaseNeedsModel {
        return baseNeedRepo.getNeeds()
    }


}