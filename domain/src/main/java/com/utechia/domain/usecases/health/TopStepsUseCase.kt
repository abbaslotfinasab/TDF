package com.utechia.domain.usecases.health


interface TopStepsUseCase<R> {

    suspend fun execute(start:String,end:String):MutableList<R>
    suspend fun getChart(start:String,end:String):MutableList<R>


}