package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.BuildConfig
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.ProfilePictureModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProfilePicture(

    val url: String?,
    val name:String?,
    val path:String?,

    ): Parcelable, ResponseObject<ProfilePictureModel> {
    override fun toDomain(): ProfilePictureModel {
        return ProfilePictureModel("${ BuildConfig.BASE_URL}$url",name,path)
    }
}