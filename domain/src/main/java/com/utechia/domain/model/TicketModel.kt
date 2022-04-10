package com.utechia.domain.model

data class TicketModel(

    val id: Int?,
    val title: String?,
    val Priority: String?,
    val status: String?,
    val fid: String?,
    val description: String?,
    val mediaurl: List<String>?,
    val datetime: String?,
    val dateupdate: String?,
    val rateable: Boolean?,
    val category: CategoryModel,
    val department: String?,
    val requestername: String?,
    val adminname: String?,

    )