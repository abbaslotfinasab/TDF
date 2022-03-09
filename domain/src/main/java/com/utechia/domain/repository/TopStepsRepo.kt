package com.utechia.domain.repository

import com.utechia.domain.model.TopStepsModel

interface TopStepsRepo {

    suspend fun getTopUser(start:String,end:String):MutableList<TopStepsModel>
    suspend fun getChart(start:String,end:String):MutableList<TopStepsModel>

}