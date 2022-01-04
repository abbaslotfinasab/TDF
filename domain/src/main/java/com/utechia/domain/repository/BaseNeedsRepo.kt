package com.utechia.domain.repository

import com.utechia.domain.model.BaseNeedsModel

interface BaseNeedsRepo {

    suspend fun getNeeds():BaseNeedsModel

}