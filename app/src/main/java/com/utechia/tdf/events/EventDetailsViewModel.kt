package com.utechia.tdf.events

import androidx.lifecycle.LiveData
import com.utechia.domain.utile.Result
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.event.EventModel
import com.utechia.domain.usecases.event.EventDetailsUsCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val eventDetailsUsCaseImpl: EventDetailsUsCaseImpl
):ViewModel() {

    private val _event = MutableLiveData<Result<MutableList<EventModel>>>()
    val event:LiveData<Result<MutableList<EventModel>>>
        get() = _event

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _event.postValue(exception.message?.let { Result.Error(it)})
    }


    fun getEvent(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _event.postValue(Result.Loading)

            eventDetailsUsCaseImpl.get(id).let {

                _event.postValue(Result.Success(it))

            }
        }
    }

    fun applyEvent(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _event.postValue(Result.Loading)

            eventDetailsUsCaseImpl.apply(id).let {

                _event.postValue(Result.Success(it))
            }
        }
    }

    fun cancelEvent(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _event.postValue(Result.Loading)

            eventDetailsUsCaseImpl.cancel(id).let {

                _event.postValue(Result.Success(it))
            }
        }
    }

    fun rateEvent(id:Int,rate:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _event.postValue(Result.Loading)

            eventDetailsUsCaseImpl.rate(id,rate).let {

                _event.postValue(Result.Success(it))
            }
        }
    }
}