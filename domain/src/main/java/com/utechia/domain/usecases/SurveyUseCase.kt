package com.utechia.domain.usecases

interface SurveyUseCase<R> {

    suspend fun getSurveyList():MutableList<R>
    suspend fun getSurvey(id:Int):MutableList<R>

}