package com.utechia.tdf.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.FavoriteModel
import com.utechia.domain.model.NotificationCountModel
import com.utechia.domain.usecases.FavoriteUseCaseImpl
import com.utechia.domain.usecases.MainUseCaseImpl
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
}