package com.utechia.domain.model.survey


data class QuestionModel(
    val id: Int?,
    val options: MutableList<String>?,
    val rate: Any?,
    val ratestars: Any?,
    val text:Any?,
    val title: String?,
    val type: String?

)
