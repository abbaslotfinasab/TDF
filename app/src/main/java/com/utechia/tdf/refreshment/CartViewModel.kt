package com.utechia.tdf.refreshment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.ItemModel
import com.utechia.domain.usecases.CartUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCaseImpl: CartUseCaseImpl
):ViewModel() {

    private val _cartModel = MutableLiveData<Result<MutableList<ItemModel>>>()
    val cartModel: LiveData<Result<MutableList<ItemModel>>>
        get() = _cartModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _cartModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getCart(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _cartModel.postValue(Result.Loading)

            cartUseCaseImpl.getCart().let {



            }

        }

    }

    fun postCart(id:Int,quantity:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _cartModel.postValue(Result.Loading)

            cartUseCaseImpl.postCart(id,quantity)

        }

    }

    fun updateCart(id:Int,quantity:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _cartModel.postValue(Result.Loading)

            cartUseCaseImpl.updateCart(id,quantity)

        }

    }


    fun deleteCart(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _cartModel.postValue(Result.Loading)

            cartUseCaseImpl.deleteCart(id)

        }

    }

















}