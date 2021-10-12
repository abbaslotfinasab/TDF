package com.utechia.tdf.viewmodel

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.moodel.ReservationModel
import com.utechia.domain.usecases.ReservationUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(

    private val reservationUseCaseImpl: ReservationUseCaseImpl


):ViewModel(){

    private val _reservationModel = MutableLiveData<Result<MutableList<ReservationModel>>>()
    val reservationModel: LiveData<Result<MutableList<ReservationModel>>>
        get() = _reservationModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _reservationModel.postValue(exception.message?.let { Result.Error(it) })
    }


    fun getBooked(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            reservationUseCaseImpl.getAll().let {

                _reservationModel.postValue(Result.Success(it))

            }


        }
    }

}