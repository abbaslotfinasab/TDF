package com.utechia.tdf.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.cart.OfficeModel
import com.utechia.domain.usecases.cart.OfficeUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfficeViewModel @Inject constructor(
    private val officeUseCaseImpl: OfficeUseCaseImpl
):ViewModel() {

    private val _officeModel = MutableLiveData<Result<MutableList<OfficeModel>>>()
    val officeModel: LiveData<Result<MutableList<OfficeModel>>>
        get() = _officeModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _officeModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getOffice(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _officeModel.postValue(Result.Loading)

            officeUseCaseImpl.execute().let {

                _officeModel.postValue(Result.Success(it))
            }
        }
    }
}