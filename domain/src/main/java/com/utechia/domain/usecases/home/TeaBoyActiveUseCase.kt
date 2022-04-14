package com.utechia.domain.usecases.home

interface TeaBoyActiveUseCase<R> {

    suspend fun execute():MutableList<R>
}