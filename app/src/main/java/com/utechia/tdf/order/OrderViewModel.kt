package com.utechia.tdf.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.OrderDataModel
import com.utechia.domain.usecases.OrderUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(

    private val orderUseCaseImpl: OrderUseCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<MutableList<OrderDataModel>>>()
    val orderModel: LiveData<Result<MutableList<OrderDataModel>>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getOrder(status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.execute(status).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }

    fun cancelOrder(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            orderUseCaseImpl.cancel(id)
        }
    }

}