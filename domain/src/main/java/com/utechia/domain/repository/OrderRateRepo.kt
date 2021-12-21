package com.utechia.domain.repository

interface OrderRateRepo {

    suspend fun rate(order:Int,rate:Int):Boolean

}