package com.utechia.domain.usecases

import com.utechia.domain.model.AnswerModel
import org.json.JSONArray


interface SurveyUseCase<R> {

    suspend fun getSurveyList():MutableList<R>
    suspend fun getSurvey(id:Int):MutableList<R>
    suspend fun getEvaluate():MutableList<R>
    suspend fun postAnswer(answer: JSONArray):MutableList<R>


}