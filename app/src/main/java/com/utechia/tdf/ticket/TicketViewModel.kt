package com.utechia.tdf.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.ticket.TicketModel
import com.utechia.domain.usecases.ticket.TicketUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketUseCaseImpl: TicketUseCaseImpl
):ViewModel() {

    private val _ticketModel = MutableLiveData<Result<LiveData<PagingData<TicketModel>>>>()
    val ticketModel: LiveData<Result<LiveData<PagingData<TicketModel>>>>
        get() = _ticketModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _ticketModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getAllTicket(status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.getAllTicket(status).cachedIn(viewModelScope).let {

                _ticketModel.postValue(Result.Success(it))

            }

        }

    }
}