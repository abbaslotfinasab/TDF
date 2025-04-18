package com.utechia.domain.usecases.ticket

import com.utechia.domain.model.ticket.BaseNeedsModel
import com.utechia.domain.repository.ticket.BaseNeedsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseNeedsUseCaseImpl @Inject constructor(private val baseNeedRepo: BaseNeedsRepo)
    : BaseNeedsUseCase {
    override suspend fun execute(): BaseNeedsModel {
        return baseNeedRepo.getNeeds()
    }


}