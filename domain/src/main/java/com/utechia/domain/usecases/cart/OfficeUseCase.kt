package com.utechia.domain.usecases.cart

interface OfficeUseCase<R> {

    suspend fun execute():MutableList<R>
}