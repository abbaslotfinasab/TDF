package com.utechia.domain.usecases.login

import com.utechia.domain.model.login.VerifyModel

interface VerifyUseCase {

    suspend fun verify(code:String): VerifyModel

}