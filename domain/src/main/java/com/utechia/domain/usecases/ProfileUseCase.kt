package com.utechia.domain.usecases

interface ProfileUseCase<R> {

    suspend fun execute():R

}