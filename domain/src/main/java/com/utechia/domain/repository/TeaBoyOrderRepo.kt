package com.utechia.domain.repository

import com.utechia.domain.model.TeaBoyOrderDataModel

interface TeaBoyOrderRepo {

    suspend fun acceptOrder(id:Int):MutableList<TeaBoyOrderDataModel>
    suspend fun rejectOrder(id:Int):MutableList<TeaBoyOrderDataModel>
    suspend fun deliveredOrder(id:Int)
    suspend fun getTeaBoyOrder(status:String):MutableList<TeaBoyOrderDataModel>
    suspend fun singleOrderTeaBoy(id:Int):MutableList<TeaBoyOrderDataModel>

}