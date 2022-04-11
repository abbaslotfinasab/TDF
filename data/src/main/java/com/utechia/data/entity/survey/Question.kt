package com.utechia.data.entity.survey

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.survey.QuestionModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Question(
    val id: Int?,
    val options: MutableList<String>?,
    val rate: @Contextual @RawValue  Any?,
    val ratestars: @Contextual @RawValue  Any?,
    val text: @Contextual @RawValue  Any?,
    val title: String?,
    val type: String?
):Parcelable,ResponseObject<QuestionModel> {
    override fun toDomain(): QuestionModel {
        return QuestionModel(id,options,rate,ratestars,text,title,type)
    }
}