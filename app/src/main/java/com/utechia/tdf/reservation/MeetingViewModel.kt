package com.utechia.tdf.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.model.reservation.MeetingModel
import com.utechia.domain.usecases.notification.NotificationUseCaseImpl
import com.utechia.domain.usecases.reservation.MeetingUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val meetingUseCaseImpl: MeetingUseCaseImpl

):ViewModel() {

    private val _meetingModel = MutableLiveData<Result<LiveData<PagingData<MeetingModel>>>>()
    val meetingModel: LiveData<Result<LiveData<PagingData<MeetingModel>>>>
        get() = _meetingModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _meetingModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getAllMeeting(status:String) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _meetingModel.postValue(Result.Loading)

            meetingUseCaseImpl.execute(status).cachedIn(viewModelScope).let {

                _meetingModel.postValue(Result.Success(it))
            }
        }
    }
}