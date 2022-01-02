package com.utechia.domain.model

data class TicketModel(

    val id: Int?,
    val Priority: String?,
    val category: CategoryModel,
    val description: String?,
    val fid: String?,
    val status: String?,
    val floor: String?,
    val mediaurl: List<String>?,
    val title: String?,
    val datetime: String?,

    )