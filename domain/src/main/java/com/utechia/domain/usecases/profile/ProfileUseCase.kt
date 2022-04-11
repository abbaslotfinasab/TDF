package com.utechia.domain.usecases.profile

interface ProfileUseCase<R> {

    suspend fun execute():R

}