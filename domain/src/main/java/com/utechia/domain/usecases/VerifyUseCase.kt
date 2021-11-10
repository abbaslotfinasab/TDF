package com.utechia.domain.usecases

import com.utechia.domain.model.VerifyModel

interface VerifyUseCase {

    suspend fun verify(code:String):VerifyModel

}