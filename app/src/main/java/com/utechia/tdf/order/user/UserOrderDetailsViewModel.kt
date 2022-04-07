package com.utechia.tdf.order.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.UserOrderDataModel
import com.utechia.domain.usecases.UserOrderDetailsUSeCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserOrderDetailsViewModel @Inject constructor(

    private val orderUseCaseImpl: UserOrderDetailsUSeCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<MutableList<UserOrderDataModel>>>()
    val userOrderModel: LiveData<Result<MutableList<UserOrderDataModel>>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        exception.message?.let { Result.Error(it) }
    }



    fun cancelOrder(id:Int){


        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.cancel(id).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }

    fun singleOrder(id:Int){

        _orderModel.postValue(Result.Loading)

        viewModelScope.launch(Dispatchers.IO+handler) {

            orderUseCaseImpl.singleOrder(id).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }

    fun setRate(order:Int,rate:Int){

        _orderModel.postValue(Result.Loading)

        viewModelScope.launch(Dispatchers.IO+handler) {

            orderUseCaseImpl.rate(order,rate).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }
}