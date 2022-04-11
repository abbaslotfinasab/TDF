package com.utechia.domain.usecases.ticket

import com.utechia.domain.model.ticket.BaseNeedsModel

interface BaseNeedsUseCase {

    suspend fun execute(): BaseNeedsModel

}