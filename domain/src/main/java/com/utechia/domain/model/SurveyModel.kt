package com.utechia.domain.model

import kotlinx.parcelize.RawValue

data class SurveyModel(

    val createdAt: String?,
    val dateend: String?,
    val datestart: String?,
    val enableedit: Boolean?,
    val id: Int?,
    val iscancelled: Boolean?,
    val isexpierd: Boolean?,
    val isinprogress: Boolean?,
    val iswatting: Boolean?,
    val questions: MutableList<QuestionModel>?,
    val surveystatus: String?,
    val surveytype: String?,
    val systemtype: String?,
    val title: String?,
    val updatedAt: String?

)