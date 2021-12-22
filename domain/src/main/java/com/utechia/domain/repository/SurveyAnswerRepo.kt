package com.utechia.domain.repository

import org.json.JSONArray

interface SurveyAnswerRepo {

    suspend fun postAnswer(answer: JSONArray):Boolean

}