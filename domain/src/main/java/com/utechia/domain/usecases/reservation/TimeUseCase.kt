package com.utechia.domain.usecases.reservation


interface TimeUseCase<R> {
    suspend fun execute(roomId:Int,date:String):MutableList<R>
}