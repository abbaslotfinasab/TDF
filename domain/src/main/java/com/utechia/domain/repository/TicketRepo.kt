package com.utechia.domain.repository

import com.utechia.domain.model.TicketModel

interface TicketRepo {

    suspend fun getAllTicket(status:String):MutableList<TicketModel>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:String,mediaurl:MutableSet<String>):MutableList<TicketModel>
    suspend fun closeTicket(fid:Int):MutableList<TicketModel>
    suspend fun replyTicket(id:Int,mediaurl:MutableSet<String>,text:String):MutableList<TicketModel>
    suspend fun rateTicket(id:Int,rate:Int):MutableList<TicketModel>


}