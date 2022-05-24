package com.utechia.tdf.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.domain.model.reservation.TimeModel
import com.utechia.domain.usecases.reservation.InvitationUseCaseImpl
import com.utechia.domain.usecases.reservation.TimeUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimeViewModel @Inject constructor(

    private val timeUseCaseImpl: TimeUseCaseImpl

): ViewModel() {

    private val _timeModel = MutableLiveData<Result<MutableList<TimeModel>>>()
    val timeModel: LiveData<Result<MutableList<TimeModel>>>
        get() = _timeModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _timeModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getMeetingTime(date: String,roomId:Int) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _timeModel.postValue(Result.Loading)

            timeUseCaseImpl.execute(roomId,date).let {

                _timeModel.postValue(Result.Success(it))

            }
        }
    }
}
