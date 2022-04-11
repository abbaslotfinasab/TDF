package com.utechia.tdf.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.utechia.domain.model.survey.SurveyModel
import com.utechia.domain.usecases.survey.SurveyUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val  surveyUseCaseImpl: SurveyUseCaseImpl
):ViewModel() {

    private val _survey = MutableLiveData<Result<LiveData<PagingData<SurveyModel>>>>()
    val survey: LiveData<Result<LiveData<PagingData<SurveyModel>>>>
        get() = _survey

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _survey.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getSurveyList(status: String){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.getSurveyList(status).cachedIn(viewModelScope).let {

                _survey.postValue(Result.Success(it))
            }
        }
    }
}