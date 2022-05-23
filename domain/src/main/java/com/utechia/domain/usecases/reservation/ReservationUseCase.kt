package com.utechia.domain.usecases.reservation

interface ReservationUseCase {
    suspend fun add(id:Int)
    suspend fun remove(id:Int)
    suspend fun deleteAll()
}