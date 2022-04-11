package com.utechia.domain.usecases.login

import com.utechia.domain.model.login.LoginModel

interface LoginUseCase {

    suspend fun execute(): LoginModel

}