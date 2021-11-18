package com.utechia.domain.repository

import com.utechia.domain.model.RefreshmentModel

interface RefreshmentRepo {

    suspend fun getRefreshment(type:String): MutableList<RefreshmentModel>
    suspend fun search(search:String,type:String): MutableList<RefreshmentModel>
    suspend fun getCart(id:Int): MutableList<RefreshmentModel>
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)


}