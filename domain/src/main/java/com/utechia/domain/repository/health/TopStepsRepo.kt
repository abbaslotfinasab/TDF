package com.utechia.domain.repository.health

import com.utechia.domain.model.health.TopStepsModel

interface TopStepsRepo {

    suspend fun getTopUser(start:String,end:String):MutableList<TopStepsModel>
    suspend fun getChart(start:String,end:String):MutableList<TopStepsModel>

}