package com.utechia.domain.repository

import com.utechia.domain.model.AnswerModel
import com.utechia.domain.model.SurveyModel
import org.json.JSONArray

interface SurveyRepo {

    suspend fun getSurveyList():MutableList<SurveyModel>
    suspend fun getSurvey(id:Int):MutableList<SurveyModel>
    suspend fun getEvaluate():MutableList<SurveyModel>
    suspend fun postAnswer(answer: JSONArray):MutableList<SurveyModel>

}