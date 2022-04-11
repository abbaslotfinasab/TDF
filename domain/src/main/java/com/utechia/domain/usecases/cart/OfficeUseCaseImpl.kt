package com.utechia.domain.usecases.cart

import com.utechia.domain.model.cart.OfficeModel
import com.utechia.domain.repository.cart.OfficeRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeUseCaseImpl @Inject constructor(private val officeRepo: OfficeRepo):
    OfficeUseCase<OfficeModel> {
    override suspend fun execute(): MutableList<OfficeModel> {
        return officeRepo.getAllOffice()
    }


}