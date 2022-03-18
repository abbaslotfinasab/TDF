package com.utechia.domain.usecases

interface OfficeUseCase<R> {

    suspend fun execute():MutableList<R>
    suspend fun search(office:String):MutableList<R>
}