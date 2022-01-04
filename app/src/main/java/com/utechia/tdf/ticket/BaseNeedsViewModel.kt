package com.utechia.tdf.ticket

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.BaseNeedsModel
import com.utechia.domain.usecases.BaseNeedsUseCaseImpl
import com.utechia.domain.utile.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class BaseNeedsViewModel @Inject constructor(
    private val baseNeedsUseCaseImpl: BaseNeedsUseCaseImpl
):ViewModel() {

    private val _baseNeedsModel = MutableLiveData<Result<BaseNeedsModel>>()
    val baseNeedsModel: LiveData<Result<BaseNeedsModel>>
        get() = _baseNeedsModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _baseNeedsModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getNeeds() {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _baseNeedsModel.postValue(Result.Loading)

            baseNeedsUseCaseImpl.execute().let {

                _baseNeedsModel.postValue(Result.Success(it))

            }

        }

    }
}