package com.utechia.domain.usecases

import com.utechia.domain.model.OrderBodyModel

interface CheckOutUseCase {

    suspend fun execute(): OrderBodyModel

}