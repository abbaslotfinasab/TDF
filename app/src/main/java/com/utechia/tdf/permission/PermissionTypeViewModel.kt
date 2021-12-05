package com.utechia.tdf.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.usecases.PermissionTypeUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionTypeViewModel @Inject constructor( private val permissionUseCaseImpl: PermissionTypeUseCaseImpl):ViewModel() {

    private val _permissionModel = MutableLiveData<Result<MutableList<String>>>()
    val permissionModel: LiveData<Result<MutableList<String>>>
        get() = _permissionModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _permissionModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getPermission(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _permissionModel.postValue(Result.Loading)

            permissionUseCaseImpl.execute().let {

                _permissionModel.postValue(Result.Success(it))
            }
        }
    }


}