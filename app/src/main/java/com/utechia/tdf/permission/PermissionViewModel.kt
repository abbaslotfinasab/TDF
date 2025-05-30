package com.utechia.tdf.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.permission.PermissionModel
import com.utechia.domain.usecases.permission.PermissionUseCaseImpl
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

    private val _permissionModel = MutableLiveData<Result<LiveData<PagingData<PermissionModel>>>>()
    val permissionModel: LiveData<Result<LiveData<PagingData<PermissionModel>>>>
        get() = _permissionModel

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _permissionModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getPermission(status:String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _permissionModel.postValue(Result.Loading)

            permissionUseCaseImpl.execute(status).cachedIn(viewModelScope).let {

                _permissionModel.postValue(Result.Success(it))
            }
        }
    }
}