package com.utechia.tdf.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.TopStepsModel
import com.utechia.domain.usecases.TopStepsUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(

    private val topStepsUseCaseImpl: TopStepsUseCaseImpl

) : ViewModel() {

    private val _topHealthModel = MutableLiveData<Result<MutableList<TopStepsModel>>>()
    val topHealthModel: LiveData<Result<MutableList<TopStepsModel>>>
        get() = _topHealthModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _topHealthModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getTop(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _topHealthModel.postValue(Result.Loading)

            topStepsUseCaseImpl.execute().let {

                _topHealthModel.postValue(Result.Success(it))
            }
        }
    }
}