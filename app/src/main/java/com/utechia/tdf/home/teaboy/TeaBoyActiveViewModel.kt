package com.utechia.tdf.home.teaboy



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.home.TeaBoyActiveModel
import com.utechia.domain.usecases.home.TeaBoyActiveUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeaBoyActiveViewModel @Inject constructor(
    private val teaBoyActiveUseCaseImpl: TeaBoyActiveUseCaseImpl
): ViewModel() {

    private val _active = MutableLiveData<Result<MutableList<TeaBoyActiveModel>>>()
    val active: LiveData<Result<MutableList<TeaBoyActiveModel>>>
        get() = _active

    private val handler = CoroutineExceptionHandler { _, exception ->
        _active.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getActiveList() {

        viewModelScope.launch(Dispatchers.IO + handler) {
            _active.postValue(Result.Loading)

            teaBoyActiveUseCaseImpl.execute().let {

                _active.postValue(Result.Success(it))

            }


        }
    }
}