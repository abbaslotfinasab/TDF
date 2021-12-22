package com.utechia.domain.usecases

import org.json.JSONArray

interface SurveyAnswerUseCase {

    suspend fun postAnswer(answer: JSONArray):Boolean

}