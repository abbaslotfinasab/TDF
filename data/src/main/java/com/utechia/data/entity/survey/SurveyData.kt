package com.utechia.data.entity.survey

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.survey.SurveyModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SurveyData(
    val createdAt: String?,
    val dateend: String?,
    val datestart: String?,
    val id: Int?,
    val questions: @Contextual @RawValue List<Question>?,
    val surveystatus: String?,
    val surveytype: String?,
    val systemtype: String?,
    val title: String?,
    val updatedAt: String?
):Parcelable,ResponseObject<SurveyModel> {
    override fun toDomain(): SurveyModel {
        return SurveyModel(createdAt,dateend,datestart,id,questions?.map { it.toDomain() }!!.toMutableList(),surveystatus,surveytype,systemtype,title,updatedAt)
    }
}