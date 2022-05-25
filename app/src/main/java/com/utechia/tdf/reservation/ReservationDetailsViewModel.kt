package com.utechia.tdf.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.reservation.SingleMeetingModel
import com.utechia.domain.usecases.reservation.SingleMeetingUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationDetailsViewModel @Inject constructor(
    private val singleMeetingUseCaseImpl: SingleMeetingUseCaseImpl

):ViewModel() {

    private val _meeting = MutableLiveData<Result<SingleMeetingModel>>()
    val meeting: LiveData<Result<SingleMeetingModel>>
        get() = _meeting

    private val handler = CoroutineExceptionHandler { _, exception ->
        _meeting.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getSingleMeeting(id:Int) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _meeting.postValue(Result.Loading)

            singleMeetingUseCaseImpl.execute(id).let {

                _meeting.postValue(Result.Success(it))
            }
        }
    }
}