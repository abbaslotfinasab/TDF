package com.utechia.data.entity.profile

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.profile.UserModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class User(
    val displayName: String?,
    val id: Int?,
    val officeFloor: String?,
    val officeWorkStation: String?,
    val jobTitle:String?,
    val officeLocation: String?,
    val profilePicture: ProfilePicture?,
    ):Parcelable,ResponseObject<UserModel>{
    override fun toDomain(): UserModel {
        return UserModel(displayName,id,officeFloor,officeWorkStation,officeLocation,profilePicture?.toDomain())
    }
}