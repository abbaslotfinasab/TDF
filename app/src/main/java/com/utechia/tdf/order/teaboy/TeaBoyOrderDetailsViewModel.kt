package com.utechia.tdf.order.teaboy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.domain.usecases.order.TeaBoyOrderDetailsUSeCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaBoyOrderDetailsViewModel @Inject constructor(

    private val orderUseCaseImpl: TeaBoyOrderDetailsUSeCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<MutableList<TeaBoyOrderDataModel>>>()
    val orderModel: LiveData<Result<MutableList<TeaBoyOrderDataModel>>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
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

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.deliveredOrder(id).let {

                _orderModel.postValue(Result.Success(it))

            }
        }
    }
}