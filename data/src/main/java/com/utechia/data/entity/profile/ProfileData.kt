package com.utechia.data.entity.profile

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.profile.ProfileModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@Entity
data class ProfileData(
    @PrimaryKey
    val id: Int?,
    val displayName: String?,
    val officeFloor: String?,
    val officeWorkStation: String?,
    val jobTitle:String?,
    val mail:String?,
    val employeeId: Int?,
    val officeLocation: String?,
    @Embedded(prefix = "prefix_")
    val profilePicture: ProfilePicture?,
    val added:Boolean?=false

    ): Parcelable, ResponseObject<ProfileModel> {
    override fun toDomain(): ProfileModel {
        return ProfileModel(displayName,id,officeFloor,officeWorkStation,jobTitle,mail,employeeId,officeLocation,profilePicture?.toDomain(),added)
    }
}