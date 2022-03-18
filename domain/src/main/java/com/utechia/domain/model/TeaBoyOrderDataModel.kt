package com.utechia.domain.model

data class TeaBoyOrderDataModel(

    val id: Int?,
    val status: String?,
    val floor: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val location:String?,
    val cart:CartModel?,
    val user:UserModel?,

    )