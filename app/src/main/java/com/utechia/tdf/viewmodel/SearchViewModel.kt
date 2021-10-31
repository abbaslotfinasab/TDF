package com.utechia.tdf.viewmodel

import com.utechia.domain.utile.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.SearchModel
import com.utechia.domain.usecases.SearchUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

    private val searchUseCaseImpl: SearchUseCaseImpl


):ViewModel(){

    private val _searchModel = MutableLiveData<Result<MutableList<SearchModel>>>()
    val searchModel: LiveData<Result<MutableList<SearchModel>>>
        get() = _searchModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _searchModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getBooked(name:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            searchUseCaseImpl.execute(name).let {

                _searchModel.postValue(Result.Success(it))

            }
        }
    }
}