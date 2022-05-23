package com.utechia.tdf.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.profile.ProfileModel
import com.utechia.domain.usecases.reservation.InvitationUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InvitationViewModel @Inject constructor(

    private val invitationUseCaseImpl: InvitationUseCaseImpl

): ViewModel() {

    private val _profileModel = MutableLiveData<Result<MutableList<ProfileModel>>>()
    val profileModel: LiveData<Result<MutableList<ProfileModel>>>
        get() = _profileModel

    private val handler = CoroutineExceptionHandler { _, exception ->
        _profileModel.postValue(exception.message?.let { Result.Error(it) })
    }


    fun getUserList(query: String) {

        viewModelScope.launch(Dispatchers.IO + handler) {

            _profileModel.postValue(Result.Loading)

            invitationUseCaseImpl.execute(query).let {

                _profileModel.postValue(Result.Success(it))

            }
        }
    }
}
