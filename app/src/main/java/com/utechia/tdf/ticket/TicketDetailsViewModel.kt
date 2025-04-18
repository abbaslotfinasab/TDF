package com.utechia.tdf.ticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.ticket.TicketModel
import com.utechia.domain.usecases.ticket.TicketDetailsUseCaseImpl
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

    private val _ticketModel = MutableLiveData<Result<MutableList<TicketModel>>>()
    val ticketModel: LiveData<Result<MutableList<TicketModel>>>
        get() = _ticketModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _ticketModel.postValue(exception.message?.let { Result.Error(it) })
    }


    fun getSingleTicket(fid:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.getSingleTicket(fid).let {

                _ticketModel.postValue(Result.Success(it))

            }
        }
    }

    fun postTicket(description:String,title:String,category:Int,Priority:String,Floor:Int,mediaurl:MutableSet<String>){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.postTicket(description,title,category,Priority,Floor,mediaurl).let {

                _ticketModel.postValue(Result.Success(it))

            }

        }

    }

    fun closeTicket(fid:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.closeTicket(fid).let {

                _ticketModel.postValue(Result.Success(it))

            }
        }
    }

    fun replyTicket(id:Int,mediaurl:MutableSet<String>,text:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.replyTicket(id,mediaurl,text).let {

                _ticketModel.postValue(Result.Success(it))

            }
        }
    }

    fun rateTicket(ticket:Int,rate:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _ticketModel.postValue(Result.Loading)

            ticketUseCaseImpl.rateTicket(ticket,rate).let {

                _ticketModel.postValue(Result.Success(it))

            }

        }
    }
}
