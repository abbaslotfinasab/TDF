package com.utechia.tdf.reservation

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.reservation.AnswerReservationModel
import com.utechia.domain.model.reservation.ReservationModel
import com.utechia.domain.usecases.reservation.ReservationUseCaseImpl
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

    fun createMeeting(answerReservationModel: AnswerReservationModel) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _reservationModel.postValue(Result.Loading)

            reservationUseCaseImpl.create(answerReservationModel).let {

                _reservationModel.postValue(Result.Success(it))

            }
        }
    }

    fun cancelMeeting(meetId:Int) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _reservationModel.postValue(Result.Loading)

            reservationUseCaseImpl.cancel(meetId).let {

                _reservationModel.postValue(Result.Success(it))

            }
        }
    }

    fun addGuess(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _reservationModel.postValue(Result.Loading)

            reservationUseCaseImpl.add(id)
        }

    }
    fun removeGuess(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _reservationModel.postValue(Result.Loading)

            reservationUseCaseImpl.remove(id)
        }
    }

    fun deleteAll(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _reservationModel.postValue(Result.Loading)

            reservationUseCaseImpl.deleteAll()
        }
    }
}