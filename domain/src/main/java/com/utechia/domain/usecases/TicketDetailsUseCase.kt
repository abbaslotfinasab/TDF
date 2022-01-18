package com.utechia.domain.usecases

import com.utechia.domain.model.TicketModel

interface TicketDetailsUseCase {

    suspend fun getSingleTicket(id:Int):TicketModel
}