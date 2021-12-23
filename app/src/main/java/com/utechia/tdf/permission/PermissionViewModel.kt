package com.utechia.tdf.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.PermissionModel
import com.utechia.domain.usecases.PermissionUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val permissionUseCaseImpl: PermissionUseCaseImpl
):ViewModel() {

    private val _permissionModel = MutableLiveData<Result<MutableList<PermissionModel>>>()
    val permissionModel: LiveData<Result<MutableList<PermissionModel>>>
        get() = _permissionModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _permissionModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getPermission(status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _permissionModel.postValue(Result.Loading)

            permissionUseCaseImpl.execute(status).let {

                _permissionModel.postValue(Result.Success(it))
            }
        }
    }

    fun getSinglePermission(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _permissionModel.postValue(Result.Loading)

            permissionUseCaseImpl.get(id).let {

                _permissionModel.postValue(Result.Success(it))
            }
        }
    }

    fun postPermission(type:String,description:String,start:String,end:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _permissionModel.postValue(Result.Loading)

            permissionUseCaseImpl.post(type,description,start,end).let {

                _permissionModel.postValue(Result.Success(it))

            }

        }
    }

    fun updatePermission(id:Int,status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _permissionModel.postValue(Result.Loading)

            permissionUseCaseImpl.update(id,status).let {

                _permissionModel.postValue(Result.Success(it))

            }

        }
    }
}