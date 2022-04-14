package com.utechia.tdf.order.teaboy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.order.OrderCountModel
import com.utechia.domain.usecases.order.OrderCountUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderCountViewModel @Inject constructor(

    private val orderCountUseCaseImpl: OrderCountUseCaseImpl

    ):ViewModel() {

    private val _orderModel = MutableLiveData<Result<MutableList<OrderCountModel>>>()
    val orderModel: LiveData<Result<MutableList<OrderCountModel>>>
        get() = _orderModel


    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getOrder(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderCountUseCaseImpl.getOrderCount().let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }

    fun setStatus(status:Boolean,floorId:Int){
        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderCountUseCaseImpl.setStatus(status,floorId).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }
}