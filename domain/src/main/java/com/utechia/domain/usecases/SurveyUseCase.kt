package com.utechia.domain.usecases

import org.json.JSONArray

interface SurveyUseCase<R> {

    suspend fun getSurveyList():MutableList<R>
    suspend fun getSurvey(id:Int):MutableList<R>
    suspend fun getEvaluate():MutableList<R>

}