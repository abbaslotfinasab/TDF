package com.utechia.domain.repository.favorite

import com.utechia.domain.model.favorite.FavoriteModel

interface FavoriteRepo {

    suspend fun getFavorite(): MutableList<FavoriteModel>
    suspend fun getExist(title:String): MutableList<FavoriteModel>
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)

}