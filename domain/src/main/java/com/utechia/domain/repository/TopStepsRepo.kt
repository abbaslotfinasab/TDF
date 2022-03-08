package com.utechia.domain.repository

import com.utechia.domain.model.TopStepsModel

interface TopStepsRepo {

    suspend fun getTopUser():MutableList<TopStepsModel>
}