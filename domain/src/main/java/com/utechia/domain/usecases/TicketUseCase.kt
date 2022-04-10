package com.utechia.domain.usecases



interface TicketUseCase<R> {

    suspend fun getAllTicket(status:String):MutableList<R>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:Int,mediaurl:MutableSet<String>):MutableList<R>
    suspend fun closeTicket(fid:Int):MutableList<R>
    suspend fun replyTicket(id:Int,mediaurl:MutableSet<String>,text:String):MutableList<R>
    suspend fun rateTicket(id:Int,rate:Int):MutableList<R>



}