package com.utechia.domain.model


data class QuestionModel(

    val answers: MutableList<AnswerModel>?,
    val id: Int?,
    val options: MutableList<String>?,
    val rate: Any?,
    val ratestars: Any?,
    val text:Any?,
    val title: String?,
    val type: String?

)
