package com.utechia.domain.usecases


interface TopStepsUseCase<R> {

    suspend fun execute():MutableList<R>

}