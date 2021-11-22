package com.utechia.domain.usecases

import com.utechia.domain.model.CartDataModel
import com.utechia.domain.model.CartModel
import com.utechia.domain.model.ItemModel
import com.utechia.domain.repository.CartRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartUseCaseImpl @Inject constructor(private val cartRepo: CartRepo):CartUseCase<CartDataModel> {
    override suspend fun getCart(): MutableList<CartDataModel> {
        return cartRepo.getCart()
    }

    override suspend fun postCart(id: Int, quantity: Int) {
        return cartRepo.postCart(id,quantity)
    }

    override suspend fun updateCart(id: Int, quantity: Int) {
        return cartRepo.updateCart(id,quantity)
    }

    override suspend fun deleteCart(id: Int) {
        return cartRepo.deleteCart(id)
    }
}