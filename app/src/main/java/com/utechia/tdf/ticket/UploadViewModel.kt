package com.utechia.tdf.ticket

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.UploadModel
import com.utechia.domain.usecases.UploadUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject


@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadUseCaseImpl: UploadUseCaseImpl
): ViewModel() {

    private val _uploadModel = MutableLiveData<Result<UploadModel>>()
    val uploadModel: LiveData<Result<UploadModel>>
        get() = _uploadModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _uploadModel.postValue(exception.message?.let { Result.Error(it) })
    }

    fun uploadFile(uri: Uri) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _uploadModel.postValue(Result.Loading)

            uploadUseCaseImpl.execute(uri).let {

                _uploadModel.postValue(Result.Success(it))

            }

        }

    }
}