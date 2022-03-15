package com.utechia.domain.model


data class ProfileModel(
    val name: String,
    val id: Int,
    val floor: String,
    val workStation: String,
    val jobTitle:String?,
    val mail:String?,
    val profilePictureModel: ProfilePictureModel

    )