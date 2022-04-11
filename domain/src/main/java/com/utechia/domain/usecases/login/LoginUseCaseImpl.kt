package com.utechia.domain.usecases.login

import com.utechia.domain.model.login.LoginModel
import com.utechia.domain.repository.login.LoginRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCaseImpl @Inject constructor(private val loginRepo: LoginRepo): LoginUseCase {

    override suspend fun execute(): LoginModel {
        return loginRepo.getLogin()
    }

}