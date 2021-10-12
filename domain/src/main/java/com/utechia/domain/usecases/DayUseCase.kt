package com.utechia.domain.usecases

interface DayUseCase<R> {

    suspend fun execute(month_id:Int): MutableList<R>

}