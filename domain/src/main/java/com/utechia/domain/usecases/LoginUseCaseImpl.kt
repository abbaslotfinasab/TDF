package com.utechia.domain.usecases

import com.utechia.domain.model.LoginModel
import com.utechia.domain.repository.LoginRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCaseImpl @Inject constructor(private val loginRepo: LoginRepo):LoginUseCase{

    override suspend fun execute(): LoginModel {
        return loginRepo.getLogin()
    }

}