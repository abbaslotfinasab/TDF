package com.utechia.domain.usecases

import android.net.Uri
import com.utechia.domain.model.TicketModel
import com.utechia.domain.repository.TicketRepo
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketUseCaseImpl @Inject constructor(private val ticketRepo: TicketRepo):TicketUseCase<TicketModel>{
    override suspend fun getAllTicket(): MutableList<TicketModel> {
        return ticketRepo.getAllTicket()
    }

    override suspend fun postTicket(
        description: String,
        title: String,
        category: Int,
        Priority: String,
        Floor: String,
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
}