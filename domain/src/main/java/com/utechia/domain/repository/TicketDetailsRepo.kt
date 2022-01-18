package com.utechia.domain.repository

import com.utechia.domain.model.TicketModel

interface TicketDetailsRepo {

    suspend fun getSingleTicket(id:Int):TicketModel
}