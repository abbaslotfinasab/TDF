package com.utechia.domain.repository.ticket

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.utechia.domain.model.ticket.TicketModel

interface TicketRepo {

    suspend fun getAllTicket(status:String): LiveData<PagingData<TicketModel>>



}