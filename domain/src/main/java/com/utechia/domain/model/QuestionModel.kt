package com.utechia.domain.model

import kotlinx.parcelize.RawValue

data class QuestionModel(

    val answers: MutableList<AnswerModel>?,
    val id: Int?,
    val options: MutableList<String>?,
    val rate: Any?,
    val ratestars: Int?,
    val text:Any?,
    val title: String?,
    val type: String?

)
