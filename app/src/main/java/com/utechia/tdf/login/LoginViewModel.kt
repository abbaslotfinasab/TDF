package com.utechia.tdf.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.LoginModel
import com.utechia.domain.usecases.LoginUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val loginUseCaseImpl:LoginUseCaseImpl

): ViewModel(){

    private val _loginModel = MutableLiveData<Result<LoginModel>>()
    val loginModel: LiveData<Result<LoginModel>>
        get() = _loginModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _loginModel.postValue(exception.message?.let { Result.Error(it) })
    }

    init {

        getLogin()
    }

     private fun getLogin(){

        viewModelScope.launch(Dispatchers.IO+handler)  {

            _loginModel.postValue(Result.Loading)

            loginUseCaseImpl.execute().let {

                _loginModel.postValue(Result.Success(it))
            }
        }
    }
}