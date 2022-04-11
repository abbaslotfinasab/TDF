package com.utechia.domain.model.survey

import com.utechia.domain.model.survey.QuestionModel


data class SurveyModel(

    val createdAt: String?,
    val dateend: String?,
    val datestart: String?,
    val id: Int?,
    val questions: List<QuestionModel>?,
    val surveystatus: String?,
    val surveytype: String?,
    val systemtype: String?,
    val title: String?,
    val updatedAt: String?

)