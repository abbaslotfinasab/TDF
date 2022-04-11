package com.utechia.tdf.reservation

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.reservation.RoomModel
import com.utechia.domain.usecases.reservation.RoomUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(

    private val roomUseCaseImpl: RoomUseCaseImpl


):ViewModel(){

    private val _roomModel = MutableLiveData<Result<MutableList<RoomModel>>>()
    val roomModel: LiveData<Result<MutableList<RoomModel>>>
        get() = _roomModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _roomModel.postValue(exception.message?.let { Result.Error(it) })
    }

    init {
        getRoom()
    }

    private fun getRoom(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _roomModel.postValue(Result.Loading)

            delay(2000)

            roomUseCaseImpl.execute().let {

                _roomModel.postValue(Result.Success(it))

            }


        }
    }

}