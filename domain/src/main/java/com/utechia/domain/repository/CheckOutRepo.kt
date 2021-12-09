package com.utechia.domain.repository

import com.utechia.domain.model.OrderBodyModel

interface CheckOutRepo {

    suspend fun checkout(): OrderBodyModel


}