package com.utechia.domain.repository


interface OfficeRepo {

    suspend fun getAllOffice():MutableList<String>

    suspend fun getOffice(office:String):MutableList<String>


}