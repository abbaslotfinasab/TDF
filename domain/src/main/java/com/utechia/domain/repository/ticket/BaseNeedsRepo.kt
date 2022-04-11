package com.utechia.domain.repository.ticket

import com.utechia.domain.model.ticket.BaseNeedsModel

interface BaseNeedsRepo {

    suspend fun getNeeds(): BaseNeedsModel

}