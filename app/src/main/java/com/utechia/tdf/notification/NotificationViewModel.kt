package com.utechia.tdf.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.notification.NotificationModel
import com.utechia.domain.usecases.notification.NotificationUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationUseCaseImpl: NotificationUseCaseImpl

):ViewModel() {

    private val _notificationModel = MutableLiveData<Result<LiveData<PagingData<NotificationModel>>>>()
    val notificationModel: LiveData<Result<LiveData<PagingData<NotificationModel>>>>
        get() = _notificationModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _notificationModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getNotification() {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _notificationModel.postValue(Result.Loading)

            notificationUseCaseImpl.getAll().cachedIn(viewModelScope).let {

                _notificationModel.postValue(Result.Success(it))
            }
        }
    }
}