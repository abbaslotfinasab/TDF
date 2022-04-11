package com.utechia.domain.usecases.favorite

import com.utechia.domain.model.favorite.FavoriteModel
import com.utechia.domain.repository.favorite.FavoriteRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteUseCaseImpl @Inject constructor(private val favoriteRepo: FavoriteRepo):
    FavoriteUseCase<FavoriteModel> {

    override suspend fun execute(): MutableList<FavoriteModel> {
        return favoriteRepo.getFavorite()
    }

    override suspend fun like(id: Int) {
        return favoriteRepo.like(id)
    }

    override suspend fun dislike(id: Int) {
        return favoriteRepo.dislike(id)
    }

    override suspend fun exist(title:String): MutableList<FavoriteModel> {
        return favoriteRepo.getExist(title)
    }
}