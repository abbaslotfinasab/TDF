package com.utechia.tdf.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utechia.domain.model.survey.SurveyModel
import com.utechia.domain.usecases.survey.SurveyDetailsUseCaseImpl
import com.utechia.domain.utile.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import javax.inject.Inject

@HiltViewModel
class SurveyDetailsViewModel @Inject constructor(
    private val  surveyUseCaseImpl: SurveyDetailsUseCaseImpl
):ViewModel() {

    var answer:ArrayList<HashMap<String,Any>> = ArrayList()

    private val _survey = MutableLiveData<Result<MutableList<SurveyModel>>>()
    val survey: LiveData<Result<MutableList<SurveyModel>>>
        get() = _survey

    private val handler = CoroutineExceptionHandler {
            _, exception ->
        _survey.postValue(exception.message?.let { Result.Error(it) })
    }

    fun getSurvey(id:Int){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.getSurvey(id).let {

                _survey.postValue(Result.Success(it))
            }
        }
    }

    fun postAnswer(_answer:JSONArray){

        viewModelScope.launch(Dispatchers.IO+handler) {

            _survey.postValue(Result.Loading)

            surveyUseCaseImpl.postAnswer(_answer).let {

                _survey.postValue(Result.Success(it))
            }
        }
    }
}