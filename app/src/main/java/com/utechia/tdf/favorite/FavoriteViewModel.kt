package com.utechia.tdf.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.FavoriteModel
import com.utechia.domain.usecases.FavoriteUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(

    private val favoriteUseCaseImpl: FavoriteUseCaseImpl

) : ViewModel() {

    private val _favoriteModel = MutableLiveData<Result<MutableList<FavoriteModel>>>()
    val favoriteModel: LiveData<Result<MutableList<FavoriteModel>>>
        get() = _favoriteModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _favoriteModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getFavorite(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _favoriteModel.postValue(Result.Loading)

            favoriteUseCaseImpl.execute().let {

                _favoriteModel.postValue(Result.Success(it))

            }

        }
    }

    fun getExist(title:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _favoriteModel.postValue(Result.Loading)

            favoriteUseCaseImpl.exist(title).let {

                _favoriteModel.postValue(Result.Success(it))

            }

        }
    }

    fun like(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            favoriteUseCaseImpl.like(id)

        }

    }

    fun dislike(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            favoriteUseCaseImpl.dislike(id)

        }

    }
}