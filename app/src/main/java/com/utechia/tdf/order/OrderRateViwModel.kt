package com.utechia.tdf.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.usecases.OrderRateUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderRateViwModel @Inject constructor(

    private val orderRateUseCaseImpl: OrderRateUseCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<Boolean>>()
    val orderModel: LiveData<Result<Boolean>>
        get() = _orderModel


    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun setRate(order:Int,rate:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderRateUseCaseImpl.execute(order,rate).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }
}