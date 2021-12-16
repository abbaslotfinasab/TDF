package com.utechia.tdf.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.SurveyModel
import com.utechia.domain.usecases.SurveyUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonArray
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val  surveyUseCaseImpl: SurveyUseCaseImpl
):ViewModel() {

    private val _survey = MutableLiveData<Result<MutableList<SurveyModel>>>()
    val survey: LiveData<Result<MutableList<SurveyModel>>>
        get() = _survey

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _survey.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getSurveyList(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.getSurveyList().let {

                _survey.postValue(Result.Success(it))
            }
        }
    }

    fun getSurvey(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.getSurvey(id).let {

                _survey.postValue(Result.Success(it))
            }
        }
    }

    fun getEvaluate(){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.getEvaluate().let {

                _survey.postValue(Result.Success(it))
            }
        }
    }

    fun postAnswer(answer:JSONArray){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.postAnswer(answer)

        }
    }
}