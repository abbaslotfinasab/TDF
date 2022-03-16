package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.ProfileModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProfileData(
    val displayName: String?,
    val id: Int?,
    val officeFloor: String?,
    val officeWorkStation: String?,
    val jobTitle:String?,
    val mail:String?,
    val profilePicture: ProfilePicture?

    ): Parcelable, ResponseObject<ProfileModel> {
    override fun toDomain(): ProfileModel {
        return ProfileModel(displayName,id,officeFloor,officeWorkStation,jobTitle,mail,profilePicture?.toDomain())
    }
}