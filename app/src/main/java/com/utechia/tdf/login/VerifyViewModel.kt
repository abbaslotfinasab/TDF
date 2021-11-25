package com.utechia.tdf.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.VerifyModel
import com.utechia.domain.usecases.VerifyUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(

    private val verifyUseCaseImpl: VerifyUseCaseImpl

): ViewModel(){

    private val _verifyModel = MutableLiveData<Result<VerifyModel>>()
    val verifyModel: LiveData<Result<VerifyModel>>
        get() = _verifyModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _verifyModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun verify(code:String){

        viewModelScope.launch(Dispatchers.IO+handler)  {

            _verifyModel.postValue(Result.Loading)

            delay(2000)

            verifyUseCaseImpl.verify(code).let {

                _verifyModel.postValue(Result.Success(it))

            }
        }
    }
}