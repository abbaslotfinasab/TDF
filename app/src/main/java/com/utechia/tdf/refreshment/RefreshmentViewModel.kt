package com.utechia.tdf.refreshment

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.refreshment.RefreshmentModel
import com.utechia.domain.usecases.refreshment.RefreshmentUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefreshmentViewModel @Inject constructor(
    private val refreshmentUseCaseImpl: RefreshmentUseCaseImpl
):ViewModel() {

    private val _refreshmentModel = MutableLiveData<Result<MutableList<RefreshmentModel>>>()
    val refreshmentModel: LiveData<Result<MutableList<RefreshmentModel>>>
        get() = _refreshmentModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _refreshmentModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getRefreshment(category:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _refreshmentModel.postValue(Result.Loading)

            refreshmentUseCaseImpl.get(category).let {

                _refreshmentModel.postValue(Result.Success(it))

            }

        }

    }

    fun getCart(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.cart()

        }

    }

    fun search(search:String,category: String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.search(search,category).let {

                _refreshmentModel.postValue(Result.Success(it))

            }

        }

    }

    fun postCart(id:Int,quantity:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.postCart(id,quantity)

        }
    }

    fun updateCart(id:Int,quantity:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.updateCart(id,quantity)

        }
    }

    fun deleteCart(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.deleteCart(id)

        }
    }
    fun like(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.like(id)

        }

    }

    fun dislike(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.dislike(id)

        }

    }

}