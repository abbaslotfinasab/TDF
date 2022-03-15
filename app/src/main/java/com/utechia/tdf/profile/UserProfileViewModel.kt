package com.utechia.tdf.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.ProfileModel
import com.utechia.domain.usecases.ProfileUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(

    private val profileUseCaseImpl: ProfileUseCaseImpl

): ViewModel() {

    private val _profile = MutableLiveData<Result<ProfileModel>>()
    val profile: LiveData<Result<ProfileModel>>
        get() = _profile

    private val handler = CoroutineExceptionHandler { _, exception ->
        _profile.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getProfile() {

        viewModelScope.launch(Dispatchers.IO+handler) {

            _profile.postValue(Result.Loading)

            profileUseCaseImpl.execute().let {

                _profile.postValue(Result.Success(it))

            }
        }
    }
}