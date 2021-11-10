package com.utechia.domain.usecases

import com.utechia.domain.model.LoginModel

interface LoginUseCase {

    suspend fun execute(): LoginModel

}