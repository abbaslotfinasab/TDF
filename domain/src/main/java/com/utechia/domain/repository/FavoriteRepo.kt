package com.utechia.domain.repository

import com.utechia.domain.model.FavoriteModel

interface FavoriteRepo {

    suspend fun getFavorite(): MutableList<FavoriteModel>
    suspend fun getExist(title:String): MutableList<FavoriteModel>
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)

}