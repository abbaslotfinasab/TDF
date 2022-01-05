package com.utechia.domain.usecases

import android.net.Uri
import org.json.JSONArray


interface TicketUseCase<R> {

    suspend fun getAllTicket():MutableList<R>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:String,mediaurl:List<String>):MutableList<R>
    suspend fun closeTicket(fid:Int):MutableList<R>
    suspend fun replyTicket(id:Int,mediaurl:JSONArray,text:String):MutableList<R>


}