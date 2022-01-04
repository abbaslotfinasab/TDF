package com.utechia.domain.repository

import android.net.Uri
import com.utechia.domain.model.TicketModel

interface TicketRepo {

    suspend fun getAllTicket():MutableList<TicketModel>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:String,mediaurl:List<Uri>):MutableList<TicketModel>
    suspend fun closeTicket(fid:Int):MutableList<TicketModel>
    suspend fun replyTicket(id:Int,mediaurl:List<Uri>,text:String):MutableList<TicketModel>

}