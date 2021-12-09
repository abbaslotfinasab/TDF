package com.utechia.domain.usecases

import com.utechia.domain.model.OrderBodyModel
import com.utechia.domain.repository.CheckOutRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckOutUseCaseImpl @Inject constructor(private val checkOutRepo: CheckOutRepo):CheckOutUseCase {

    override suspend fun execute(): OrderBodyModel {
        return checkOutRepo.checkout()
    }
}