package com.utechia.domain.model.profile

import com.utechia.domain.model.profile.ProfilePictureModel

data class UserModel (

    val displayName: String?,
    val id: Int?,
    val officeFloor: String?,
    val officeWorkStation: String?,
    val officeLocation: String?,
    val profilePictureModel: ProfilePictureModel?,
    )