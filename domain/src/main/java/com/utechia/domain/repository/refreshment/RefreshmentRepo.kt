package com.utechia.domain.repository.refreshment

import com.utechia.domain.model.refreshment.RefreshmentModel

interface RefreshmentRepo {

    suspend fun getRefreshment(type: String): MutableList<RefreshmentModel>
    suspend fun search(search: String, type: String): MutableList<RefreshmentModel>
    suspend fun getCart(): MutableList<RefreshmentModel>
    suspend fun postCart(id: Int, quantity: Int)
    suspend fun updateCart(id: Int, quantity: Int)
    suspend fun deleteCart(id: Int)
    suspend fun like(id:Int)
    suspend fun dislike(id:Int)
}