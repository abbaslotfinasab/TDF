package com.utechia.tdf.order.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.UserOrderDataModel
import com.utechia.domain.usecases.UserOrderUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserOrderViewModel @Inject constructor(

    private val orderUseCaseImpl: UserOrderUseCaseImpl

):ViewModel() {

    private val _orderModel = MutableLiveData<Result<LiveData<PagingData<UserOrderDataModel>>>>()
    val userOrderModel: LiveData<Result<LiveData<PagingData<UserOrderDataModel>>>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        exception.message?.let { Result.Error(it) }
    }

    fun getOrder(status:String){


        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            orderUseCaseImpl.execute(status).let {

                _orderModel.postValue(Result.Success(it))

                it.cachedIn(viewModelScope)

            }
        }
    }
}