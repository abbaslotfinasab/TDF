package com.utechia.domain.usecases.cart

import com.utechia.domain.model.cart.CartModel
import com.utechia.domain.repository.cart.CartRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartUseCaseImpl @Inject constructor(private val cartRepo: CartRepo): CartUseCase {
    override suspend fun getCart(): MutableList<CartModel> {
        return cartRepo.getCart()
    }

    override suspend fun postCart(id: Int, quantity: Int) {
        return cartRepo.postCart(id,quantity)
    }

    override suspend fun updateCart(id: Int, quantity: Int) {
        return cartRepo.updateCart(id,quantity)
    }

    override suspend fun deleteCart(id: Int):MutableList<CartModel> {
        return cartRepo.deleteCart(id)
    }

    override suspend fun execute(location : String,floor:Int): MutableList<CartModel> {
        return cartRepo.checkout(location,floor)
    }


}