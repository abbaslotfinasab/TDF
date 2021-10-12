package com.utechia.tdf.viewmodel

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.moodel.DayModel
import com.utechia.domain.usecases.DayUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(

    private val dayUseCaseImpl: DayUseCaseImpl


):ViewModel(){

    private val _dayModel = MutableLiveData<Result<MutableList<DayModel>>>()
    val dayModel: LiveData<Result<MutableList<DayModel>>>
        get() = _dayModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _dayModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getDay(month_id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            dayUseCaseImpl.execute(month_id).let {

                _dayModel.postValue(Result.Success(it))

            }


        }
    }

}