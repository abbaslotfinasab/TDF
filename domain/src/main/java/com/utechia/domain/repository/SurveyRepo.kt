package com.utechia.domain.repository

import com.utechia.domain.model.SurveyModel

interface SurveyRepo {

    suspend fun getSurveyList():MutableList<SurveyModel>
    suspend fun getSurvey(id:Int):MutableList<SurveyModel>
    suspend fun getEvaluate():MutableList<SurveyModel>
}