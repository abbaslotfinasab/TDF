package com.utechia.domain.usecases.survey

interface SurveyUseCase<R> {

    suspend fun getSurveyList(status:String):R

}