package com.utechia.data.entity.survey

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.survey.AnswerModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
data class Answer(

    val id: Int?,
    val option: String?,
    val rate: Int?,
    val text: String?,

    ):Parcelable,ResponseObject<AnswerModel> {
    override fun toDomain(): AnswerModel {
        return AnswerModel(id,option,rate,text)
    }

}