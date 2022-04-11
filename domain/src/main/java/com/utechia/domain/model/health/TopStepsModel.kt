package com.utechia.domain.model.health

import com.utechia.domain.model.profile.UserModel


data class TopStepsModel(

    val id : Int?,
    val userid: Int?,
    val count: Int?,
    val cal: Int?,
    val createdAt: String?,
    val UpdatedAt: String?,
    val user : UserModel


)