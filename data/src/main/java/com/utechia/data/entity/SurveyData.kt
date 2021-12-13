package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.SurveyModel
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
    val enableedit: Boolean?,
    val id: Int?,
    val iscancelled: Boolean?,
    val isexpierd: Boolean?,
    val isinprogress: Boolean?,
    val iswatting: Boolean?,
    val questions: @Contextual @RawValue MutableList<Question>?=null,
    val surveystatus: String?,
    val surveytype: String?,
    val systemtype: String?,
    val title: String?,
    val updatedAt: String?
):Parcelable,ResponseObject<SurveyModel> {
    override fun toDomain(): SurveyModel {
        return SurveyModel(createdAt,dateend,datestart,enableedit,id,iscancelled,isexpierd,isinprogress,iswatting,questions?.map { it.toDomain() }!!.toMutableList(),surveystatus,surveytype,systemtype,title,updatedAt)
    }
}