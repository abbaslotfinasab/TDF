package com.utechia.domain.usecases

import com.utechia.domain.repository.OfficeRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeUseCaseImpl @Inject constructor(private val officeRepo: OfficeRepo):
    OfficeUseCase<String> {
    override suspend fun execute(): MutableList<String> {
        return officeRepo.getAllOffice()
    }

    override suspend fun search(office:String): MutableList<String> {
        return officeRepo.getOffice(office)
    }
}