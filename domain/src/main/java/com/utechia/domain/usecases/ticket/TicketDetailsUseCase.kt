package com.utechia.domain.usecases.ticket


interface TicketDetailsUseCase<R> {

    suspend fun getSingleTicket(id:Int): MutableList<R>
    suspend fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:Int,mediaurl:MutableSet<String>):MutableList<R>
    suspend fun closeTicket(fid:Int):MutableList<R>
    suspend fun replyTicket(id:Int,mediaurl:MutableSet<String>,text:String):MutableList<R>
    suspend fun rateTicket(id:Int,rate:Int):MutableList<R>
}