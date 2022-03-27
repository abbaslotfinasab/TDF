package com.utechia.tdf.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.NotificationModel
import com.utechia.domain.usecases.NotificationUseCaseImpl
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

    private val _notificationModel = MutableLiveData<Result<MutableList<NotificationModel>>>()
    val notificationModel: LiveData<Result<MutableList<NotificationModel>>>
        get() = _notificationModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _notificationModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getNotification() {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _notificationModel.postValue(Result.Loading)

            notificationUseCaseImpl.getAll().let {

                _notificationModel.postValue(Result.Success(it))
            }
        }
    }

    fun readNotification(id: Int,readAll:Boolean?=false) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _notificationModel.postValue(Result.Loading)

            notificationUseCaseImpl.read(id,readAll).let {

                _notificationModel.postValue(Result.Success(it))
            }
        }
    }

    fun deleteNotification(id: Int,deleteAll:Boolean?=false) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _notificationModel.postValue(Result.Loading)

            notificationUseCaseImpl.delete(id,deleteAll).let {

                _notificationModel.postValue(Result.Success(it))
            }
        }
    }
}