package com.utechia.domain.usecases.ticket

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.ticket.TicketModel
import com.utechia.domain.repository.ticket.TicketRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketUseCaseImpl @Inject constructor(private val ticketRepo: TicketRepo):
    TicketUseCase<LiveData<PagingData<TicketModel>>> {

    override suspend fun getAllTicket(status:String): LiveData<PagingData<TicketModel>> {
        return ticketRepo.getAllTicket(status)
    }

}