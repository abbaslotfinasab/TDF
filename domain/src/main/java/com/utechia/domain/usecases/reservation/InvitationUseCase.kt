package com.utechia.domain.usecases.reservation

interface InvitationUseCase<R> {
    suspend fun execute(query:String):MutableList<R>
}