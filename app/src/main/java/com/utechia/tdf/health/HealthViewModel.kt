package com.utechia.tdf.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.TopStepsModel
import com.utechia.domain.model.UserModel
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

    private val data:MutableList<TopStepsModel> = mutableListOf()

    fun getTop(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _topHealthModel.postValue(Result.Loading)

            data.clear()

            data.add(TopStepsModel(0,133,300,15,"","", UserModel("TestFN TestLN",133,"","")))
            data.add(TopStepsModel(0,133,300,15,"","", UserModel("TestFN TestLN",133,"","")))
            data.add(TopStepsModel(0,133,300,15,"","", UserModel("TestFN TestLN",133,"","")))
            data.add(TopStepsModel(0,133,300,15,"","", UserModel("TestFN TestLN",133,"","")))
            data.add(TopStepsModel(0,133,300,15,"","", UserModel("TestFN TestLN",133,"","")))


            _topHealthModel.postValue(Result.Success(data))

        }
    }
}