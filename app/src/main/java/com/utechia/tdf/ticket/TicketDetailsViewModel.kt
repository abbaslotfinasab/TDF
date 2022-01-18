package com.utechia.tdf.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.TicketModel
import com.utechia.domain.usecases.TicketDetailsUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketDetailsViewModel @Inject constructor(
    private val ticketUseCaseImpl: TicketDetailsUseCaseImpl
):ViewModel() {

    private val _ticketModel = MutableLiveData<Result<TicketModel>>()
    val ticketModel: LiveData<Result<TicketModel>>
        get() = _ticketModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _ticketModel.postValue(exception.message?.let { Result.Error(it) })
    }


    fun getSingleTicket(id: Int) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.getSingleTicket(id).let {

                _ticketModel.postValue(Result.Success(it))

            }

        }

    }
}
