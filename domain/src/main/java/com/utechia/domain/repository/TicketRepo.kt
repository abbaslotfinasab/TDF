package com.utechia.domain.repository

import com.utechia.domain.model.TicketModel

interface TicketRepo {

    suspend fun getAllTicket():MutableList<TicketModel>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:String,mediaurl:List<String>):MutableList<TicketModel>
    suspend fun closeTicket(fid:Int):MutableList<TicketModel>

}