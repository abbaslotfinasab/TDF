package com.utechia.domain.usecases

import android.net.Uri
import com.utechia.domain.model.TicketModel
import org.json.JSONArray


interface TicketUseCase<R> {

    suspend fun getAllTicket(status:String):MutableList<R>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:String,mediaurl:MutableSet<String>):MutableList<R>
    suspend fun closeTicket(fid:Int):MutableList<R>
    suspend fun replyTicket(id:Int,mediaurl:MutableSet<String>,text:String):MutableList<R>
    suspend fun rateTicket(id:Int,rate:Int):MutableList<R>



}