package com.utechia.domain.usecases.ticket



interface TicketUseCase<R> {

    suspend fun getAllTicket(status:String):R




}