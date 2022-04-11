package com.utechia.domain.repository.survey

import com.utechia.domain.model.survey.SurveyModel
import org.json.JSONArray

interface SurveyDetailsRepo {

    suspend fun getSurvey(id:Int):MutableList<SurveyModel>
    suspend fun postAnswer(answer: JSONArray):MutableList<SurveyModel>
}