package com.utechia.tdf.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.main.NotificationCountModel
import com.utechia.domain.usecases.main.MainUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val mainUseCaseImpl: MainUseCaseImpl

) : ViewModel() {

    private val _mainModel = MutableLiveData<Result<NotificationCountModel>>()
    val mainModel: LiveData<Result<NotificationCountModel>>
        get() = _mainModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _mainModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getCount(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _mainModel.postValue(Result.Loading)

            mainUseCaseImpl.count().let {

                _mainModel.postValue(Result.Success(it))

            }
        }
    }

    fun sendToken(){

        viewModelScope.launch(Dispatchers.IO+handler) {

         mainUseCaseImpl.sendToken()

        }
    }

    private val _stepCounter = MutableLiveData<Int>()
    val stepCounter: LiveData<Int>
        get() = _stepCounter


    fun stepCount(steps:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _stepCounter.postValue(steps)

        }
    }

    private val _orderCounter = MutableLiveData<Int>()
    val orderCounter: LiveData<Int>
        get() = _orderCounter


    fun orderCount(orders:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _orderCounter.postValue(orders)

        }
    }

    fun sendSteps(steps:Int,calory:Int,start:String,end:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            mainUseCaseImpl.sendSteps(steps,calory,start,end)

        }

    }
}