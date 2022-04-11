package com.utechia.domain.repository.ticket

import com.utechia.domain.model.login.VerifyModel

interface VerifyRepo {

    suspend fun verifyLogin(code:String): VerifyModel
}