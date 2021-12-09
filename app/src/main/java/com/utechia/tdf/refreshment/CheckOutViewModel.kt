package com.utechia.tdf.refreshment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.CartModel
import com.utechia.domain.model.OrderBodyModel
import com.utechia.domain.usecases.CheckOutUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val checkOutUseCaseImpl: CheckOutUseCaseImpl
):ViewModel() {

    private val _orderModel = MutableLiveData<Result<OrderBodyModel>>()
    val orderModel: LiveData<Result<OrderBodyModel>>
        get() = _orderModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _orderModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun checkoutCart(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderModel.postValue(Result.Loading)

            checkOutUseCaseImpl.execute().let {

                _orderModel.postValue(Result.Success(it))
            }

        }

    }

}