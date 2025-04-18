package com.utechia.domain.usecases.ticket

import com.utechia.domain.model.ticket.TicketModel
import com.utechia.domain.repository.ticket.TicketDetailsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketDetailsUseCaseImpl @Inject constructor(private val ticketRepo: TicketDetailsRepo):
    TicketDetailsUseCase<TicketModel> {

    override suspend fun getSingleTicket(id: Int): MutableList<TicketModel> {
        return ticketRepo.getSingleTicket(id)
    }

    override suspend fun postTicket(
        description: String,
        title: String,
        category: Int,
        Priority: String,
        Floor: Int,
        mediaurl: MutableSet<String>
    ): MutableList<TicketModel> {
        return ticketRepo.postTicket(description,title,category,Priority,Floor,mediaurl)
    }

    override suspend fun closeTicket(fid: Int): MutableList<TicketModel> {
        return ticketRepo.closeTicket(fid)
    }

    override suspend fun replyTicket(
        id: Int,
        mediaurl: MutableSet<String>,
        text: String
    ): MutableList<TicketModel> {
        return ticketRepo.replyTicket(id,mediaurl,text)
    }

    override suspend fun rateTicket(id: Int, rate: Int): MutableList<TicketModel> {
        return ticketRepo.rateTicket(id,rate)
    }

}
