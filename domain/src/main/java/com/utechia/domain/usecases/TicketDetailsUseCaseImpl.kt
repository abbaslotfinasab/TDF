package com.utechia.domain.usecases

import com.utechia.domain.model.TicketModel
import com.utechia.domain.repository.TicketDetailsRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketDetailsUseCaseImpl @Inject constructor(private val ticketRepo: TicketDetailsRepo):
    TicketDetailsUseCase {

    override suspend fun getSingleTicket(id: Int): TicketModel {
        return ticketRepo.getSingleTicket(id)
    }

}
