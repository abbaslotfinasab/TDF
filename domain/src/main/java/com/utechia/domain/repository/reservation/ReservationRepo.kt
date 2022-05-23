package com.utechia.domain.repository.reservation


interface ReservationRepo {

    suspend fun addGuess(id:Int)
    suspend fun removeGuess(id:Int)
    suspend fun deleteAll()

}