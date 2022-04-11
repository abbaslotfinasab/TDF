package com.utechia.domain.usecases.login

import com.utechia.domain.model.login.VerifyModel
import com.utechia.domain.repository.ticket.VerifyRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VerifyUseCaseImpl @Inject constructor(private val verifyRepo: VerifyRepo): VerifyUseCase {

    override suspend fun verify(code: String): VerifyModel {
        return verifyRepo.verifyLogin(code)
    }


}