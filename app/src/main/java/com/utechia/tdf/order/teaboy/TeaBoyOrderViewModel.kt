package com.utechia.tdf.order.teaboy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.order.TeaBoyOrderDataModel
import com.utechia.domain.usecases.order.TeaBoyOrderUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaBoyOrderViewModel @Inject constructor(

    private val orderUseCaseImpl: TeaBoyOrderUseCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<LiveData<PagingData<TeaBoyOrderDataModel>>>>()
    val orderModel: LiveData<Result<LiveData<PagingData<TeaBoyOrderDataModel>>>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getOrderTeaBoy(status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.getOrder(status).cachedIn(viewModelScope).let {

                _orderModel.postValue(Result.Success(it))
            }
        }
    }
}