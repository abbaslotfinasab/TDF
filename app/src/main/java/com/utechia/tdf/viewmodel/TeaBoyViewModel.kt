package com.utechia.tdf.viewmodel

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.usecases.TeaBoyUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaBoyViewModel @Inject constructor(
    private val teaBoyUseCaseImpl: TeaBoyUseCaseImpl
):ViewModel() {

    private val _teaBoyModel = MutableLiveData<Result<MutableList<RefreshmentModel>>>()
    val teaBoyModel: LiveData<Result<MutableList<RefreshmentModel>>>
        get() = _teaBoyModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _teaBoyModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getRefreshment(category:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            teaBoyUseCaseImpl.getRefreshment(category).let {

                _teaBoyModel.postValue(Result.Success(it))

            }

        }

    }

    fun getCard(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            teaBoyUseCaseImpl.getCard(id).let {

                _teaBoyModel.postValue(Result.Success(it))

            }

        }

    }

    fun like(refreshmentModel: RefreshmentModel){

        viewModelScope.launch(Dispatchers.IO+handler) {

            teaBoyUseCaseImpl.like(refreshmentModel)

        }

    }

    fun delete(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            teaBoyUseCaseImpl.delete(id)

        }

    }


    fun cancel(refreshmentModel: RefreshmentModel){

        viewModelScope.launch(Dispatchers.IO+handler) {

            teaBoyUseCaseImpl.cancel(refreshmentModel)

        }

    }

    fun order(refreshmentModel: MutableList<RefreshmentModel>){

        viewModelScope.launch(Dispatchers.IO+handler) {

            teaBoyUseCaseImpl.order(refreshmentModel)

        }

    }

}