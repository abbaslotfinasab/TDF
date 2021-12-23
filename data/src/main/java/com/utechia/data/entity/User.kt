package com.utechia.data.entity

import android.os.Parcelable
import com.utechia.data.base.ResponseObject
import com.utechia.domain.model.UserModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class User(
    val displayName: String,
    val id: Int,
    val officeFloor: String,
    val officeWorkStation: String
):Parcelable,ResponseObject<UserModel>{
    override fun toDomain(): UserModel {
        return UserModel(displayName,id,officeFloor,officeWorkStation)
    }
}