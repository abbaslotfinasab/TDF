package com.utechia.domain.usecases

import com.utechia.domain.model.BaseNeedsModel

interface BaseNeedsUseCase {

    suspend fun execute(): BaseNeedsModel

}