package com.utechia.tdf.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.TopHealth
import com.utechia.domain.utile.Result
import com.utechia.tdf.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(


) : ViewModel() {

    private val _topHealthModel = MutableLiveData<Result<MutableList<TopHealth>>>()
    val topHealthModel: LiveData<Result<MutableList<TopHealth>>>
        get() = _topHealthModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _topHealthModel.postValue(exception.message?.let { Result.Error(it) })
    }

    private val data:MutableList<TopHealth> = mutableListOf()

    fun getTop(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _topHealthModel.postValue(Result.Loading)

            data.clear()

            data.add(TopHealth(0,"john",300,15, R.drawable.ic_user.toString()))
            data.add(TopHealth(0,"john",300,15, R.drawable.ic_user.toString()))
            data.add(TopHealth(0,"john",300,15, R.drawable.ic_user.toString()))
            data.add(TopHealth(0,"john",300,15, R.drawable.ic_user.toString()))
            data.add(TopHealth(0,"john",300,15, R.drawable.ic_user.toString()))


            _topHealthModel.postValue(Result.Success(data))

        }
    }
}