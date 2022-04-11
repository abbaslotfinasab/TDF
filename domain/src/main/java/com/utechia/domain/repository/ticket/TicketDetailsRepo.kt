package com.utechia.domain.repository.ticket

import com.utechia.domain.model.ticket.TicketModel

interface TicketDetailsRepo {

    suspend fun getSingleTicket(id:Int):MutableList<TicketModel>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:Int,mediaurl:MutableSet<String>):MutableList<TicketModel>
    suspend fun closeTicket(fid:Int):MutableList<TicketModel>
    suspend fun replyTicket(id:Int,mediaurl:MutableSet<String>,text:String):MutableList<TicketModel>
    suspend fun rateTicket(id:Int,rate:Int):MutableList<TicketModel>
}