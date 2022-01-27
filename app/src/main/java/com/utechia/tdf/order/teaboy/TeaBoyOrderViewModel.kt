package com.utechia.tdf.order.teaboy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.TeaBoyOrderDataModel
import com.utechia.domain.usecases.TeaBoyOrderUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaBoyOrderViewModel @Inject constructor(

    private val orderUseCaseImpl: TeaBoyOrderUseCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<MutableList<TeaBoyOrderDataModel>>>()
    val orderModel: LiveData<Result<MutableList<TeaBoyOrderDataModel>>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getOrderTeaBoy(status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.getOrder(status).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }
    fun singleOrderTeaBoy(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.singleOrderTeaBoy(id).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }


    fun acceptOrder(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.acceptOrder(id).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }



    fun rejectOrder(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.rejectOrder(id).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }

    fun deliverOrder(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            orderUseCaseImpl.deliveredOrder(id)

        }
    }
}