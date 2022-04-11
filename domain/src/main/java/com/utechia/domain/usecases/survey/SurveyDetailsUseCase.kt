package com.utechia.domain.usecases.survey

import org.json.JSONArray


interface SurveyDetailsUseCase<R> {

    suspend fun getSurvey(id:Int):MutableList<R>
    suspend fun postAnswer(answer: JSONArray):MutableList<R>


}