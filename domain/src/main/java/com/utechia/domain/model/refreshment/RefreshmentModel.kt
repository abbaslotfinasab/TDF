package com.utechia.domain.model.refreshment

data class RefreshmentModel(

    val category: String?,

    val createdAt: String?,

    val id: Int?,

    val imageName: String?,

    val imagePath: String?,

    val rating: Int?,

    val status: Boolean?,

    val title: String?,

    val updatedAt: String?,

    var like:Boolean?,

    var open:Boolean?,

    var number:Int=0,



    )