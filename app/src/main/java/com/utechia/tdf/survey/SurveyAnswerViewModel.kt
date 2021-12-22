package com.utechia.tdf.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.usecases.SurveyAnswerUseCaeImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class SurveyAnswerViewModel @Inject constructor(
    private val  surveyUseCaseImpl: SurveyAnswerUseCaeImpl
):ViewModel() {

    private val _survey = MutableLiveData<Result<Boolean>>()
    val survey: LiveData<Result<Boolean>>
        get() = _survey

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _survey.postValue(exception.message?.let { Result.Error(it) })
    }

    fun postAnswer(answer:JSONArray){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.postAnswer(answer).let {
                _survey.postValue(Result.Success(it))
            }

        }
    }
}