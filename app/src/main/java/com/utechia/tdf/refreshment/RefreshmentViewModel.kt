package com.utechia.tdf.refreshment

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.RefreshmentModel
import com.utechia.domain.usecases.RefreshmentUseCaseImpl
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

    fun search(search:String,category: String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.search(search,category).let {

                _refreshmentModel.postValue(Result.Success(it))

            }

        }

    }

    fun getCard(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            refreshmentUseCaseImpl.cart(id).let {

                _refreshmentModel.postValue(Result.Success(it))

            }

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

    fun delete(id:Int){


    }


    fun cancel(refreshmentModel: RefreshmentModel){



    }

    fun order(refreshmentModel: MutableList<RefreshmentModel>){



    }

}