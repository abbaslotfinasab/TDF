package com.utechia.domain.model.profile


data class ProfileModel(
    val name: String?,
    val id: Int?,
    val floor: String?,
    val workStation: String?,
    val jobTitle:String?,
    val mail:String?,
    val employeeId: Int?,
    val officeLocation: String?,
    val profilePictureModel: ProfilePictureModel?,
    var added:Boolean?=false

    )