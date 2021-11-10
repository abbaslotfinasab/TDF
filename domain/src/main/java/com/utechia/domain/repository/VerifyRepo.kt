package com.utechia.domain.repository

import com.utechia.domain.model.VerifyModel

interface VerifyRepo {

    suspend fun verifyLogin(code:String):VerifyModel
}